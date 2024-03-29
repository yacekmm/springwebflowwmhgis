package pdm.tree;

import java.util.ArrayList;

import java.util.Collections;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;
import java.util.Map.Entry;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.richfaces.component.html.HtmlTree;
import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;

import pdm.Utils.ColorGradient;
import pdm.Utils.Const;
import pdm.Utils.PdmLog;
import pdm.Utils.Validator;
import pdm.beans.SearchResult;
import pdm.beans.TaxElement;
import pdm.dao.FileDAO;
import pdm.dao.SearchResultDAO;
import pdm.dao.TaxElementDAO;
import pdm.interfacaces.Resetable;
import pdm.tree.concept.Concept;

/**
 * Glowna klasa aplikacji, warstwa posrednia miedzy widokiem GUI, a warstwa
 * danych (DAO)
 * 
 */
public class TreeBean implements TreeBeanInterface, Resetable {
	/**
	 * Serializacja
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Zmienna przechowuje drzewo taksonomii
	 */
	private TreeNodeImpl<TaxElement> rootNode = null;
	/**
	 * Wektor wynikow wyszukiwania - pasujacych w 100% do konceptow
	 */
	private List<SearchResult> searchResultVector;
	/**
	 * Wektor wynikow wyszukiwania - dopasowanych za pomoca konceptow
	 * przedzialowych
	 */
	private List<SearchResult> intervalSearchResultVector;
	/**
	 * Flaga informujaca o potrzebie odbudowania wynikow wyszukiwania
	 */
	private boolean resultsNeedsToBeRefreshed = false;
	/**
	 * Flaga informujaca o potrzebie odbudowania wynikow
	 * wyszukiwania(przedzialowych)
	 */
	private boolean intervalResultsNeedsToBeRefreshed = false;
	/**
	 * Zmienna przechowuje referencje do DAO taksonomii
	 */
	private TaxElementDAO taxElementDAO;
	/**
	 * Zmienna przechowuje referencje do DAO plikow(obrazow)
	 */
	private FileDAO fileDAO;
	/**
	 * Zmienna przechowuje referencje do DAO wynikow wyszukiwania
	 */
	private SearchResultDAO searchResultDAO;
	/**
	 * element wybrany w drzewie taksonomii przez uzytkownika.
	 */
	private TaxElement selectedNode = null;
	/**
	 * Aktualny koncept utworzony poprzez klikniecie przez uzytkownika wybranego
	 * elementu na drzewie taksonomii. Koncept powstaje przez polaczenie
	 * wybranego elementu z jego korzeniem i uwzglednienie wszystkich elementow
	 * pomiedzy nimi. selectedConcept jest konceptem ktory pokazywany jest w
	 * polu edycji konceptu
	 */
	private Concept concept;
	/**
	 * Lista przechowujaca kwalifikatory wyszukiwanego obiektu (wybrane do tej
	 * pory)
	 */
	private List<Concept> conceptHistory;
	/**
	 * flaga wskazujaca na to czy obecnie aplikacja dziala w trybie wyszukiwania
	 * czy indeksowania
	 */
	private boolean indexingMode = false;
	/**
	 * Wektor przechowujacy wybrany koncept taksonomii w trybie indeksowania
	 */
	private List<TaxElement> selectedTaxElements;
	/**
	 * Zmienna przechowujaca element dodawany w trybie indeksowania
	 */
	private SearchResult addedElement;

	private TreeBeanHelper tbh = new TreeBeanHelper();

	/**
	 * Konstrktor klasy
	 */
	public TreeBean() {
		concept = new Concept();
		conceptHistory = new ArrayList<Concept>();
	}

	/**
	 * Funkcja laduje drzewo taksonomii z bazy danych za posrednictwem
	 * taxElementDAO i konwertuje je do formy zrozumialej dla widoku
	 */
	private void loadTree() {
		Vector<TreeNodeImpl<TaxElement>> elements = taxElementDAO
				.getTreeObjects();

		for (int i = 0; i < elements.size(); i++) {
			for (int i2 = 0; i2 < elements.size(); i2++)
				if (elements.get(i).getData().getParentId() == elements.get(i2)
						.getData().getId())
					elements.get(i2).addChild(
							elements.get(i).getData().getId(), elements.get(i));
		}

		rootNode = new TreeNodeImpl<TaxElement>();
		try {
			for (int i = 0; i < elements.size(); i++) {
				if (elements.get(i).getData().getParentId() == 0)
					rootNode.addChild(elements.get(i).getData().getId(),
							elements.get(i));

			}
		} catch (NullPointerException e) {
			PdmLog.getLogger().error(
					"W bazie TaxElement ma null zamiast 0 w rootNode ");
		}

	}

	/**
	 * Ustawia format siatki widoku(liczbe elementow w wierszu)
	 */
	public int getSearchResultGridFormat() {
		if (getIntervalSearchResults() != null) {
			if (getSearchResults().size() < 6)
				return getSearchResults().size();
		}
		return 6;

	}

	/**
	 * Ustawia format siatki widoku(liczbe elementow w wierszu)
	 */
	public int getIntervalSearchResultGridFormat() {
		if (getIntervalSearchResults() != null) {
			if (getIntervalSearchResults().size() < 6)
				return getIntervalSearchResults().size();
		}
		return 6;
	}

	/**
	 * Funkcja zwraca wyniki wyszukiwania w zaleznosci od wybranych konceptow -
	 * tylko w 100% zgodnie
	 * 
	 * @return wektor zgodnych w 100% wynikow wyszukiwania
	 */
	public List<SearchResult> getSearchResults() {
		long time = System.currentTimeMillis();
		if (resultsNeedsToBeRefreshed) {

			searchResultVector = new ArrayList<SearchResult>();
			Vector<Integer> intSearchResultVector = new Vector<Integer>();
			TaxElement tmp = new TaxElement();

			if (conceptHistory.size() > 0) {
				TaxElement taxElem;
				Boolean green = false;
				SortedSet<TaxElement> taxGreen = new TreeSet<TaxElement>();
				ArrayList<TaxElement> taxRed = new ArrayList<TaxElement>();

				for (int i = 0; i < conceptHistory.size(); i++) {

					Integer ids = conceptHistory
							.get(i)
							.getConfirmedConcept()
							.get(conceptHistory.get(i).getConfirmedConcept()
									.size() - 1).getId();
					;
					taxElem = taxElementDAO.get(ids);

					green = conceptHistory
							.get(i)
							.getConfirmedConcept()
							.get(conceptHistory.get(i).getConfirmedConcept()
									.size() - 1).getType();
					if (green == true)
						taxGreen.addAll(tbh.allChildren(taxElem));
					if (green == false)
						taxRed.addAll(tbh.allChildren(taxElem));

				}
				taxGreen.removeAll(taxRed);
				Set<TaxElement> tmpTaxes = new HashSet<TaxElement>();
				// testowo
				int i = taxGreen.first().getRootId();
				boolean wasFull = false;

				ArrayList<Integer> tmpSearchResultList;
				for (Iterator<TaxElement> it = taxGreen.iterator(); it
						.hasNext();) {
					tmp = it.next();
					if (tmp.getRootId() == i) {
						tmpTaxes.add(tmp);
					} else {
						i = tmp.getRootId();
						if (intSearchResultVector.isEmpty()) {
							intSearchResultVector.addAll(searchResultDAO
									.check2(tmpTaxes));
							tmpTaxes.clear();
							tmpTaxes.add(tmp);
							wasFull = true;
						} else {
							tmpSearchResultList = searchResultDAO
									.check2(tmpTaxes);
							intSearchResultVector
									.retainAll(tmpSearchResultList);
							/*
							 * for (SearchResult sr : searchResultVector) { if
							 * (!tmpSearchResultList.contains(sr)) toDelete.ad
							 * searchResultVector.remove(sr);
							 * 
							 * }
							 */
							tmpTaxes.clear();
							tmpTaxes.add(tmp);
						}

					}
				}
				// ostatnie ktore nie wpadnie w else
				if (tmpTaxes.isEmpty() && tmp != null) {
					tmpTaxes.add(tmp);
				}

				if (!wasFull) {
					intSearchResultVector.addAll(searchResultDAO
							.check2(tmpTaxes));
					tmpTaxes.clear();
				} else {
					tmpSearchResultList = searchResultDAO.check2(tmpTaxes);
					intSearchResultVector.retainAll(tmpSearchResultList);
					tmpTaxes.clear();
				}

				tmpSearchResultList = searchResultDAO.check2(taxRed);
				intSearchResultVector.removeAll(tmpSearchResultList);
				searchResultVector.clear();

				searchResultVector = searchResultDAO
						.loadList(intSearchResultVector);

				resultsNeedsToBeRefreshed = false;
			}
			time = System.currentTimeMillis() - time;
			PdmLog.getLogger().info("Czas wyszukiwania: " + time + " ms. ");
		}

		return searchResultVector;

	}

	private boolean redOnly() {
		boolean redOnly = true;
		for (Concept c : conceptHistory) {
			if (c.getConfirmedConceptColor().equals(
					ColorGradient.getInstance().getIncludedColor())) {
				redOnly = false;
				break;
			}
		}

		if (conceptHistory.size() == 0)
			redOnly = false;

		return redOnly;
	}

	/**
	 * Funkcja zwraca wyniki zgodne nie w 100% - wyniki wyszukiwajace
	 * wynikiajace z konceptow przedzialowych
	 * 
	 * @return
	 */
	public List<SearchResult> getIntervalSearchResults() {
		if (intervalResultsNeedsToBeRefreshed) {

			intervalSearchResultVector = new ArrayList<SearchResult>();
			List<Integer> intIntervalSearchResultList = new ArrayList<Integer>();
			TaxElement tmp = new TaxElement();
			if (conceptHistory.size() > 0) {
				TaxElement taxElem;
				Boolean green = false;
				SortedSet<TaxElement> taxGreen = new TreeSet<TaxElement>();
				ArrayList<TaxElement> taxRed = new ArrayList<TaxElement>();

				for (int i = 0; i < conceptHistory.size(); i++) {
					for (int i2 = 0; i2 < conceptHistory.get(i)
							.getConfirmedConcept().size() ; i2++) {

						Integer ids = conceptHistory.get(i)
								.getConfirmedConcept().get(i2).getId();
						;
						taxElem = taxElementDAO.get(ids);

						green = conceptHistory.get(i).getConfirmedConcept()
								.get(i2).getType();
						if (green != null)
							if (green == true)
								taxGreen.addAll(tbh.allChildren(taxElem));

					}

				}
				for (int i = 0; i < conceptHistory.size(); i++) {

					Integer ids = conceptHistory
							.get(i)
							.getConfirmedConcept()
							.get(conceptHistory.get(i).getConfirmedConcept()
									.size() - 1).getId();
					;
					taxElem = taxElementDAO.get(ids);

					green = conceptHistory
							.get(i)
							.getConfirmedConcept()
							.get(conceptHistory.get(i).getConfirmedConcept()
									.size() - 1).getType();

					if (green == false)
						taxRed.addAll(tbh.allChildren(taxElem));

				}
				ArrayList<Integer> tmpSearchResultList;
				boolean wasFull = false;

				taxGreen.removeAll(taxRed);
				Set<TaxElement> tmpTaxes = new HashSet<TaxElement>();
				// testowo
				if (!taxGreen.isEmpty()) {
					int i = taxGreen.first().getRootId();

					for (Iterator<TaxElement> it = taxGreen.iterator(); it
							.hasNext();) {
						tmp = it.next();
						if (tmp.getRootId() == i) {
							tmpTaxes.add(tmp);
						} else {
							i = tmp.getRootId();
							if (intIntervalSearchResultList.isEmpty()) {
								intIntervalSearchResultList
										.addAll(searchResultDAO
												.check2(tmpTaxes));
								wasFull = true;
								tmpTaxes.clear();
								tmpTaxes.add(tmp);
							} else {
								tmpSearchResultList = searchResultDAO
										.check2(tmpTaxes);
								intIntervalSearchResultList
										.retainAll(tmpSearchResultList);

								tmpTaxes.clear();
								tmpTaxes.add(tmp);
							}

						}
					}
					// ostatnie ktore nie wpadnie w else
					if (tmpTaxes.isEmpty() && tmp != null) {
						tmpTaxes.add(tmp);
					}
					if (!wasFull) {
						intIntervalSearchResultList.addAll(searchResultDAO
								.check2(tmpTaxes));
						tmpTaxes.clear();
					} else {
						tmpSearchResultList = searchResultDAO.check2(tmpTaxes);
						intIntervalSearchResultList
								.retainAll(tmpSearchResultList);

					}
					tmpTaxes.clear();
				}

				tmpSearchResultList = searchResultDAO.check2(taxRed);
				intIntervalSearchResultList.removeAll(tmpSearchResultList);
				intervalSearchResultVector.clear();
				intervalSearchResultVector = searchResultDAO
						.loadList(intIntervalSearchResultList);
				
				intervalSearchResultVector.removeAll(getSearchResults());
			}
			intervalResultsNeedsToBeRefreshed = false;
		}

		return intervalSearchResultVector;

	}
	


	/**
	 * Funkcja obslugujaca zaznaczenia na drzewie taksonomii
	 */
	public void processSelection(NodeSelectedEvent event) {
		if (!indexingMode) {
			// processSelectionSearchV1(event);
			processSelectionSearchV2(event);
		} else {

			HtmlTree tree = (HtmlTree) event.getComponent();

			try {
				selectedNode = (TaxElement) tree.getRowData();
				if (!selectedNode.isSelected()) {
					for (int i = 0; i < getSelectedTaxElements().size(); i++) {
						if (selectedTaxElements.get(i).getRootId()
								.equals(selectedNode.getRootId())) {
							Validator.setErrorMessage(Const.alreadySelected);
							return;
						}
					}
					selectedNode.setColor(Colors.ORANGE0.getC());
					selectedNode.setFace("standard");
					selectedNode.setSelected(true);

					selectedTaxElements.add(selectedNode);
					PdmLog.getLogger().info(
							"Adding " + selectedNode
									+ " to selectedTaxElements list");

				} else {
					selectedTaxElements.remove(selectedNode);
					PdmLog.getLogger().info(
							"Remove " + selectedNode
									+ " from selectedTaxElements list");
					selectedNode.setColor(null);
					selectedNode.setFace("standard");
					selectedNode.setSelected(false);
				}
			} catch (Exception e) {
				PdmLog.getLogger().error(
						"Blad przy obsludze zaznaczenia drzewa");
				PdmLog.getLogger().error("wyjatek: ", e);
			}
		}
	}

	/**
	 * Funkcja obsluguje zaznaczenia elementu w drzewie - kolorwanie w wersji
	 * drugiej (odwroconej do pierwszej)
	 * 
	 * @param event
	 */
	private void processSelectionSearchV2(NodeSelectedEvent event) {
		// usun kolorowanie tymczasowe niezatwierdzonego konceptu
		if (concept.getSelectedConcept().size() != 0) {
			for (TaxElement te : concept.getSelectedConcept()) {
				te.setFace(ColorGradient.getInstance().getStandardColor());
			}
		}

		// zresetuj abstractionIndexes dla konceptu
		concept.resetAbstractionIndexes();

		// odczytaj element wybrany pzez uzytkownika
		HtmlTree tree = (HtmlTree) event.getComponent();
		try {
			// wybrany element
			selectedNode = (TaxElement) tree.getRowData();
			// zbuduj koncept (od wybranego elementu do korzenia)
			List<TaxElement> selectedConcept = new ArrayList<TaxElement>();
			concept = new Concept();
			// stworz liste dzieci wybranego elementu
			extractConceptChildren(selectedNode);

			// wypelniaj wybrany koncept elementami taksonomii az do rodzica
			// oznaczajac je odpowiednimi indeksami abstrakcji
			TaxElement tmpNode = selectedNode;
			int tmpAbstractionIndex = 0;
			do {
				// ustaw abstractionIndex
				tmpNode.setAbstractionIndex(tmpAbstractionIndex);
				tmpAbstractionIndex++;

				// dodaj element do aktualnego konceptu
				selectedConcept.add(0, tmpNode);

				// pobierz kolejny element w strone rodzica
				tmpNode = tmpNode.getTreeHolder().getParent().getData();
			} while (tmpNode != null);

			concept.setSelectedConcept(selectedConcept);
			// recolourV1(selectedNode.toString());
			recolourV2(selectedNode);
			PdmLog.getLogger().info(
					"Selection Listener: " + concept.getName() + ", id: "
							+ concept.getId());
		} catch (Exception e) {
			PdmLog.getLogger().error("Blad przy obsludze zaznaczenia drzewa");
			PdmLog.getLogger().error("wyjatek: ", e);
		}
	}

	/**
	 * obsluzenie zaznaczenia elementu w drzewie - kolorwanie w wersji pierwszej
	 * 
	 * @param event
	 */
	/*
	 * private void processSelectionSearchV1(NodeSelectedEvent event) { // usun
	 * kolorowanie tymczasowe niezatwierdzonego konceptu if
	 * (concept.getSelectedConcept().size() != 0) { for (TaxElement te :
	 * concept.getSelectedConcept()) {
	 * te.setFace(ColorGradient.getInstance().getStandardColor()); } }
	 * 
	 * // odczytaj wybrany wezel HtmlTree tree = (HtmlTree)
	 * event.getComponent(); try { selectedNode = (TaxElement)
	 * tree.getRowData(); // selectedNode.setFace("orange-0"); selectedConcept =
	 * new ArrayList<TaxElement>(); concept = new Concept();
	 * extractConceptChildren(selectedNode);
	 * 
	 * // wype³niaj wybrany koncept elementami taksonomii az do rodzica //
	 * StringBuilder sb = new StringBuilder(); TaxElement tmpNode =
	 * selectedNode; do { selectedConcept.add(0, tmpNode); // sb.insert(0, ".");
	 * // sb.insert(0, selectedNode.getId()); tmpNode =
	 * tmpNode.getTreeHolder().getParent().getData(); } while (tmpNode != null);
	 * 
	 * concept.setSelectedConcept(selectedConcept);
	 * recolour(selectedNode.toString()); // concept.setId(sb.substring(0,
	 * sb.length()-1).toString()); PdmLog.getLogger().info(
	 * "Selection Listener: " + concept.getName() + ", id: " + concept.getId());
	 * } catch (Exception e) { PdmLog.getLogger().error(
	 * "Blad przy obsludze zaznaczenia drzewa");
	 * PdmLog.getLogger().error("wyjatek: ", e); } }
	 */

	/**
	 * Funkcja zapisuje dodawany element SearchResult w trybie indeksowania
	 */
	public boolean saveSearchResult() {
		if (getAddedElement().getTitle() == null
				|| getAddedElement().getTitle().equals("")
				|| getAddedElement().getDescription() == null
				|| getAddedElement().getDescription().equals("")) {
			Validator.setInfoMessage(Const.searchRecordNotCorrect);
			return false;
		}
		if (taxElementDAO.taxonomiesCount() != getSelectedTaxElements().size()) {
			Validator.setInfoMessage(Const.notAllSelected);
			return false;
		}

		if (getAddedElement().getDescription().length() > 999)
			getAddedElement().setDescription(
					getAddedElement().getDescription().substring(0, 999));
		getAddedElement().getTaxElements().addAll(selectedTaxElements);
		searchResultDAO.saveOrUpdate(getAddedElement());
		//TODO check what for it was
	/*	for (int i = 0; i < selectedTaxElements.size(); i++) {
			selectedTaxElements.get(i).getSearchResults()
					.add(getAddedElement());
			taxElementDAO.saveOrUpdate(selectedTaxElements.get(i));
		}*/

		reset();
		return true;
	}

	/**
	 * Funkcja rozszerza edytowany koncept o nowy element wersja druga -
	 * odwrocony gradient
	 * 
	 * @param newElement
	 *            element o ktory nalezy rozszerzzyc koncept
	 */
	public void extendConceptV2(TaxElement newElement) {
		PdmLog.getLogger().info(
				"Got the new Element!!! It is: " + newElement.getData());
		extractConceptChildren(newElement);
		concept.getSelectedConcept().add(newElement);
		// selectedConcept = concept.getSelectedConcept();
	}

	/*
	 * public void extendConceptV1(TaxElement newElement, String colorToSet) {
	 * PdmLog.getLogger().info( "Got the new Element!!! It is: " +
	 * newElement.getData()); newElement.setFace(colorToSet);
	 * extractConceptChildren(newElement); selectedConcept.add(newElement);
	 * concept.setSelectedConcept(selectedConcept); }
	 */

	/**
	 * Funkcja buduje liste dzieci wskazanego elementu
	 * 
	 * @param element
	 *            dla ktorego poszuiwane beda dzieci
	 */
	private void extractConceptChildren(TaxElement specificEnd) {
		Iterator<Entry<Object, TreeNode<TaxElement>>> test = specificEnd
				.getTreeHolder().getChildren();
		List<TaxElement> conceptChildren = new ArrayList<TaxElement>();
		while (test.hasNext()) {
			conceptChildren.add((TaxElement) test.next().getValue().getData());
		}
		concept.setConceptChildren(conceptChildren);
	}

	/**
	 * Funkcja osluguje zdarzenia zatwierdzenia edytowanego konceptu
	 * 
	 * @param currentFace
	 *            obecny kolor elementow w koncepcie
	 * @param faceToSet
	 *            kolor na jaki nalezy ustawic elementy w koncepcie
	 */
	public void conceptConfirmed(String currentFace, String faceToSet) {
		resultsNeedsToBeRefreshed = true;
		intervalResultsNeedsToBeRefreshed = true;
		// sprawdz czy koncept nie jest pusty
		if (concept.getSelectedConcept().size() == 0) {
			PdmLog.getLogger().warn("Koncept pusty. anuluje potwierdzanie");
			Validator.setInfoMessage("Nie wybrano konceptu");
			return;
		}

		// przeprowadz walidacje
		int validationResult = validate(faceToSet);
		if (validationResult > 0) {
			if (validationResult == 1)
				Validator.setErrorMessage(Const.VAL_MODE_1);
			else if (validationResult == 2)
				Validator.setErrorMessage(Const.VAL_MODE_2);
			else if (validationResult == 3)
				Validator.setErrorMessage(Const.VAL_MODE_3);
			else if (validationResult == 4)
				Validator.setErrorMessage(Const.VAL_MODE_4);
			else if (validationResult == 5)
				Validator.setErrorMessage(Const.VAL_MODE_5);
			else if (validationResult == 6)
				Validator.setErrorMessage(Const.VAL_MODE_6);
			else
				Validator.setErrorMessage("Inny blad walidacji ("
						+ validationResult + ")");
		}

		// w przeciwnym wypadku, gdy walidacja powiodla sie, to
		// wykonaj zadana operacje
		else {
			PdmLog.getLogger().info(
					"actionListener: " + concept.getName().toString());

			StringBuilder sb = new StringBuilder();
			TaxElement te = new TaxElement();

			// usun kolorowanie tymczasowe (pomaranczowe) i zastap go kolorem
			// zielonym ale nie w drzewie tylko w kwalifikatorach
			for (int i = concept.getSelectedConcept().size() - 1; i >= 0; i--) {
				te = concept.getSelectedConcept().get(i);
				if (te.getFace().contains(currentFace)) {
					sb = new StringBuilder();
					sb.append(faceToSet);
					sb.append(te.getFace().substring(te.getFace().indexOf("-"),
							te.getFace().length()));
					PdmLog.getLogger().info(
							"Changing face from: " + te.getFace() + " to: "
									+ sb.toString());
					te.setFace(sb.toString());
				}
			}

			// zachowaj stan kolorow elementow konceptu w celu przechowania go w
			// historii
			concept.freezeConceptToHistory();

			// jesli koncept juz wczesniej byl wybrany w historii to nadpisz go,
			// zapoibegajac
			// zdublowaniu
			// poszukaj tego konceptu w historii
			int duplicateIndex = -1;
			for (int i = 0; i < conceptHistory.size(); i++) {
				if (((Concept) conceptHistory.get(i)).getId().equals(
						concept.getId())) {
					duplicateIndex = i;
					break;
				}
			}
			// jesli koncept byl w historii to go usun
			if (duplicateIndex >= 0)
				conceptHistory.remove(duplicateIndex);

			// dodanie nowego konceptu
			addConceptToQualifier(concept);
			// conceptHistory.add(concept);

			concept = new Concept();
		}
	}

	/**
	 * Funkcja walidujaca - wywolywana w chwili potwierdzenia konceptu
	 * 
	 * @param faceToSet
	 *            zmienna wskazujaca czy koncept ma byc 'included' czy
	 *            'excluded' z kwalifikatora
	 * @return kod wskazujacy na wynik walidaci i przyczyne bledu: 0 - walidacja
	 *         zakonczona pomyslnie - mozna przetwarzac dalej 1 - proba
	 *         WLACZENIA do kwalifikatora obiektu, ktorego RODZIC juz jest
	 *         WLACZONY (nie przyniesie to zadnego efektu dla logiki) 2 - proba
	 *         WYLACZENIA z kwalifikatora obiektu, ktorego RODZIC juz jest
	 *         WYLACZONY (nie przyniesie to zadnego efektu dla logiki) 3 - proba
	 *         WLACZENIA do kwalifikatora obiektu, ktorego RODZIC jest WYLACZONY
	 *         (sprzeczne logicznie, odwrotna sytuacja jest mozliwa) 4 - proba
	 *         WYLACZENIA z kwalifikatora obiektu, ktorego DZIECKO jest WLACZONE
	 *         (sprzeczne logicznie, odwrotna sytuacja jest mozliwa) 5 - proba
	 *         WYLACZENIA z kwalifikatora obiektu, ktorego RODZIC juz jest
	 *         WYLACZONY (nie przyniesie to zadnego efektu dla logiki) 6 -
	 *         pierwszy wybrany koncept musi byc 'dolaczony'
	 */
	private int validate(String faceToSet) {
		// szukaj rodzicow i odczyaj ich 'kolor'
		String parentFace = isMyParentAmongQualifiers(concept.getId());
		String childrenFace = isMyChildAmongQualifiers(concept.getId());

		PdmLog.getLogger().info(
				"rozpoczynam walidacje - faceToSet: '" + faceToSet
						+ "', parentFace: '" + parentFace
						+ "', childrenFace: '" + childrenFace + "'.");

		// aby uniknac wyjatkow - zlikwiduje ewentualne wartosci null
		if (parentFace == null)
			parentFace = "";
		if (childrenFace == null)
			childrenFace = "";

		// 0 - ani rodzica ani dziecka nie ma na liscie - wynik walidacji
		// pomyslny
		// if (parentFace.equals("") && childrenFace.equals("")) {
		// PdmLog.getLogger()
		// .info("walidacja 0: nie ma zatwierdzonych rodzicow ani dzieci - walidacja pomyslna.");
		// return 0;
		// }

		// 1 - proba WLACZENIA do kwalifikatora obiektu, ktorego RODZIC juz jest
		// WLACZONY
		if (parentFace.contains(Const.includedColor)
				&& faceToSet.contains(Const.includedColor)) {
			PdmLog.getLogger()
					.info("walidacja 1: proba WLACZENIA do kwalifikatora obiektu, ktorego RODZIC juz jest WLACZONY");
			return 1;
		}

		// 2 - proba WYLACZENIA z kwalifikatora obiektu, ktorego RODZIC juz jest
		// WYLACZONY
		else if (parentFace.contains(Const.excludedColor)
				&& faceToSet.contains(Const.excludedColor)) {
			PdmLog.getLogger()
					.info("walidacja 2: - proba WYLACZENIA z kwalifikatora obiektu, ktorego RODZIC juz jest WYLACZONY");
			return 2;
		}

		// 3 - proba WLACZENIA do kwalifikatora obiektu, ktorego RODZIC jest
		// WYLACZONY
		else if (parentFace.contains(Const.excludedColor)
				&& faceToSet.contains(Const.includedColor)) {
			PdmLog.getLogger()
					.info("walidacja 3: proba WLACZENIA do kwalifikatora obiektu, ktorego RODZIC jest WYLACZONY");
			return 3;
		}

		// 4 - proba WYLACZENIA z kwalifikatora obiektu, ktorego DZIECKO jest
		// WLACZONE
		else if (childrenFace.contains(Const.includedColor)
				&& faceToSet.contains(Const.excludedColor)) {
			PdmLog.getLogger()
					.info("walidacja 4: proba WYLACZENIA z kwalifikatora obiektu, ktorego DZIECKO jest WLACZONE");
			return 4;
		}

		// 5 - proba WYLACZENIA z kwalifikatora obiektu, ktorego RODZIC juz jest
		// WYLACZONY
		else if (childrenFace.contains(Const.excludedColor)
				&& faceToSet.contains(Const.excludedColor)) {
			PdmLog.getLogger()
					.info("walidacja 5: proba WYLACZENIA z kwalifikatora obiektu, ktorego RODZIC juz jest WYLACZONY");
			return 5;
		}

		// 6 - pierwszy wybrany koncept nie moze byc konceptem wykluczonym
		// (czerwonym)
		// WYLACZONY
		else if (faceToSet.contains(Const.excludedColor)) {
			boolean redOnly = redOnly();
			if (conceptHistory.size() == 0 || redOnly) {

				PdmLog.getLogger()
						.info("walidacja 6: nie mozna wykluczyc wszystkich konceptow (wybierz co najmniej jeden dolaczony)");
				return 6;
			}
		}

		// jesli koncept przeszedl walidacje i jest wykluczany to nie moze byc
		// przedzialowy - usun przedzialowosc
		if (faceToSet.contains(Const.excludedColor)) {
			PdmLog.getLogger()
					.info("jesli koncept wykluczany - sprawdzam i ew. usuwam przedzialowosc");
			int lastElementIndex = concept.getSelectedConcept().size() - 1;
			conceptEditing(concept.getSelectedConcept().get(lastElementIndex)
					.getData());
		}

		// domyslnie zwroc zero - OK
		PdmLog.getLogger().info("walidacja 0: pomyslna.");
		return 0;
	}

	/**
	 * Funkcja przeszukuje zatwierdzone kwalifikatory obiektow w celu
	 * poszukiwania DZIECKA danego konceptu
	 * 
	 * @param id
	 *            identyfikator konceptu, ktorego dziecka szukamy
	 * @return parentFace (kolor wskazujacy czy dziecko aktualnego konceptu jest
	 *         WLACZONE czy WYLACZONE z kwalifikatora), lub 'null' jesli dziecka
	 *         nie ma na liscie kwalifikatorow
	 */
	private String isMyChildAmongQualifiers(String myId) {
		PdmLog.getLogger().info("szukam wsystkich dzieci konceptu: " + myId);
		// szukaj wsrod dzieci id 'nizszego' niz id rodzica
		String substringToFind = myId + ".";
		// zmienna zawiera kolory wszystkich dzieci sukanego konceptu np.
		// "redredgreenred..."
		String allFoundColors = null;

		StringBuilder sb = new StringBuilder();

		// przeszukaj koncepty zatwierdzone do kwalifikatora
		for (Concept c : conceptHistory) {
			if (c.getId().contains(substringToFind)) {
				PdmLog.getLogger().info(
						"znalazlem dziecko tego konceptu: " + c.getId());
				sb.append(c.getConfirmedConceptColor());
			}
		}

		allFoundColors = sb.toString();
		if (allFoundColors.length() > 0) {
			PdmLog.getLogger().info(
					"zwracam kolory wszystich dzieci konceptu: "
							+ allFoundColors);
			return allFoundColors;
		} else {
			PdmLog.getLogger().info("Nie znaleziono dzieci konceptu: " + myId);
			return null;
		}
	}

	/**
	 * Funkcja przeszukuje zatwierdzone kwalifikatory obiektow w celu
	 * poszukiwania RODZICA danego konceptu
	 * 
	 * @param id
	 *            identyfikator konceptu, ktorego rodzica szukamy
	 * @return parentFace (kolor wskazujacy czy rodzic aktualnego konceptu jest
	 *         WLACZONY czy WYLACZONY z kwalifikatora), lub 'null' jesli rodzica
	 *         nie ma na liscie kwalifikatorow
	 */
	private String isMyParentAmongQualifiers(String myId) {
		PdmLog.getLogger().info(
				"Rozpoczynam szukanie rodzica konceptu o id: " + myId);

		String myParentId = myId;
		String parentColorToReturn = null;

		while (myParentId.contains(".")) {
			// przejdz do szukania rodzica o poziom wyzej
			myParentId = myParentId.substring(0, myParentId.lastIndexOf("."));
			PdmLog.getLogger().info(
					"szukam w kwalifikatorach obiektu o id: " + myParentId);

			// przeszukaj czy na tym poziomie jest juz znaleziony rodzic
			parentColorToReturn = findConceptWithThisId(myParentId);

			// jesli znaleziono rodzica, to zwroc jego kolor
			if (parentColorToReturn != null)
				return parentColorToReturn;
		}

		PdmLog.getLogger()
				.info("Nie znaleziono rodzica konceptu o id: " + myId);
		return null;
	}

	/**
	 * Funckcja przeszukuje zatwierdzone kwalifikatory w poszukiwaniu konceptu o
	 * zadanym conceptId
	 * 
	 * @param conceptId
	 *            poszukiwany identyfikator
	 * @return kolor znalezionego konceptu wskazujacy czy koncept byl WLACZONY
	 *         do kwalifikatora czy WYLACZONY, zwraca null, gdy tego konceptu
	 *         nie ma na liscie
	 */
	private String findConceptWithThisId(String conceptId) {
		for (Concept c : conceptHistory) {
			if (c.getId().equals(conceptId)) {
				String foundConceptColor = c.getConfirmedConceptColor();
				PdmLog.getLogger().info(
						"Znaleziono koncept o zadanym id: " + conceptId
								+ ", kolor: " + foundConceptColor);
				return foundConceptColor;
			}
		}
		return null;
	}

	/**
	 * Funkcja przekolorujowuje elementy konceptu (gradient w wersji drugiej -
	 * odwroconej)
	 * 
	 * @param elementName
	 */
	public void recolourV2(TaxElement element) {
		updateAbstractionIndexes(element);
		PdmLog.getLogger().info("Recoloured!");
	}

	/**
	 * Funkcja aktualizuje abstractionIndex po edycji konceptu
	 * 
	 * @param element
	 */
	public void updateAbstractionIndexes(TaxElement element) {
		int conceptLength = concept.getSelectedConcept().size();
		int abstractionIndexToSet = 0;
		boolean selectedElementSpotted = false;

		for (int i = conceptLength - 1; i >= 0; i--) {
			if (!selectedElementSpotted) {
				concept.getSelectedConcept().get(i)
						.setAbstractionIndex(abstractionIndexToSet++);
				// jesli napotkales w liscie element ktory zostal klikniety -
				// przestan kolorowac (zmien flage)
				if (concept.getSelectedConcept().get(i).equals(element)) {
					selectedElementSpotted = true;
				}
			} else {
				concept.getSelectedConcept().get(i).setAbstractionIndex(-1);
			}
		}

		// uaktualnij kolory w interfejsie na bazie abstractionIndex
		updateFacesInCurrentConcept();
	}

	/**
	 * Funkcja aktualizuje styl w elementach drzewa i edytowanego konceptu style
	 * (face) odpowiada glownie za kolor zaznaczenia w drzewie i oknie edycji
	 */
	private void updateFacesInCurrentConcept() {
		for (TaxElement te : concept.getSelectedConcept()) {
			if (te.getAbstractionIndex() == -1) {
				te.setFace(ColorGradient.getInstance().getStandardColor());
			} else {
				te.setFace(ColorGradient.getInstance().getNeutralColor() + "-"
						+ te.getAbstractionIndex());
			}
		}
	}

	/**
	 * przekoloruj elementy konceptu (gradient w wersji pierwszej -
	 * nieodwroconej)
	 * 
	 * @param elementName
	 */
	/*
	 * public void recolourV1(String elementName) { String color =
	 * ColorGradient.getInstance().getStandardColor(); boolean recolour = false;
	 * int index = 0; StringBuilder sb;
	 * 
	 * // przejrzyj wszystkie wezly konceptu for (TaxElement te :
	 * concept.getSelectedConcept()) { // jezeli wezel nie jest zablokowany
	 * przed kolorwaniem (np. przez // inny wybrany wczesniej koncept)
	 * 
	 * // zaznacz kolorem tymczasowym wybrane wezly // jesli user zawezil wybor
	 * to odmaluj odznaczone wezly if
	 * (!te.getFace().equals(ColorGradient.getInstance().getStandardColor())) {
	 * te.setFace(color); PdmLog.getLogger().info(
	 * "Recolouring from green to standard: " + te.getData()); }
	 * 
	 * // jesli dotarles do wezla kliknietego przez uzytkownika zacznij //
	 * kolorowac if (te.getData().equals(elementName)) { recolour = true; } // i
	 * wezly polozone ponizej zacznij malowac na kolor if (recolour) { sb = new
	 * StringBuilder();
	 * sb.append(ColorGradient.getInstance().getNeutralColor()); sb.append("-");
	 * sb.append(index++); color = sb.toString(); te.setFace(color); }
	 * PdmLog.getLogger().info( "Recolouring: " + te.getData() + ", to face: " +
	 * te.getFace()); } PdmLog.getLogger().info("Recoloured!"); }
	 */

	public void editHistConcept(String conceptId) {
		// wymus odswiezenie wynikow wyszukiwania
		resultsNeedsToBeRefreshed = true;
		intervalResultsNeedsToBeRefreshed = true;
		// usun kolorowanie (abstractionIndexy) obecnie edytowanego konceptu
		concept.setElementFaces(ColorGradient.getInstance().getStandardColor());
		// concept.resetAbstractionIndexes();

		for (Concept c : conceptHistory) {
			if (c.getId().equals(conceptId)) {
				PdmLog.getLogger().info(
						"Zmieniam koncept z " + concept.getName() + " na "
								+ c.getName());
				concept = c;
				concept.unfreezeConceptFromHistory();
				break;
			}
		}

		StringBuilder sb = new StringBuilder();
		for (TaxElement te : concept.getSelectedConcept()) {
			if (te.getFace().contains("-")) {
				sb = new StringBuilder();
				sb.append(ColorGradient.getInstance().getNeutralColor());
				sb.append("-");
				sb.append(te.getFace().substring(te.getFace().indexOf("-") + 1,
						te.getFace().length()));
				te.setFace(sb.toString());
			}
		}
	}

	/**
	 * Funkcja usuwa koncept z kwalifikatorow obiektow (konceptow wczesniej
	 * zatwierdzonych)
	 * 
	 * @param conceptId
	 *            id conceptu ktory nalezy usunac
	 */
	public void removeHistConcept(String conceptId) {
		resultsNeedsToBeRefreshed = true;
		intervalResultsNeedsToBeRefreshed = true;
		// znajdz na liscie zatwierdzonych konceptow ten, ktory nalezy usunac
		int index = -1;
		int numGreen = 0, numRed = 0;
		boolean greenDeleted = false;
		boolean freeToDelete = true;

		for (Concept c : conceptHistory) {
			if (c.getConfirmedConceptColor().equals(
					ColorGradient.getInstance().getExcludedColor()))
				numRed++;
			else
				numGreen++;

			if (c.getId().equals(conceptId)) {
				index = conceptHistory.indexOf(c);
				if (c.getConfirmedConceptColor().equals(
						ColorGradient.getInstance().getIncludedColor()))
					greenDeleted = true;
			}
		}

		// zapobiegaj zeby nie zostay same czerwone koncepty
		if (greenDeleted && numGreen == 1 && numRed > 0)
			freeToDelete = false;

		// jesli znaleziono koncept
		if (index >= 0) {
			// wersja ktora pozwala usunac ostatni ielony koncept i tylko
			// wyswietla komunikat o samych czerwonych
			// usun z listy
			conceptHistory.remove(index);

			// uaktualnij grupowanie po taksonomiach
			groupByTaxName();
			if (!freeToDelete) {
				PdmLog.getLogger()
						.info("walidacja 6: nie mozna wykluczyc wszystkich konceptow (wybierz co najmniej jeden dolaczony)");
				Validator.setErrorMessage(Const.VAL_MODE_6);
			}

			// wersja ktora nie pozwala usunac ostatniego zielonego konceptu
			// if(freeToDelete){
			// // usun z listy
			// conceptHistory.remove(index);
			//
			// // uaktualnij grupowanie po taksonomiach
			// groupByTaxName();
			// } else {
			// PdmLog.getLogger().info("walidacja 6: nie mozna wykluczyc wszystkich konceptow (wybierz co najmniej jeden dolaczony)");
			// Validator.setErrorMessage(Const.VAL_MODE_6);
			// }
		}

		// if(conceptHistory.size()==0){
		// wymus odswiezenie strony (bo sa problemy z onclick=submit();
		FacesContext context = FacesContext.getCurrentInstance();
		String viewId = context.getViewRoot().getViewId();
		ViewHandler handler = context.getApplication().getViewHandler();
		UIViewRoot root = handler.createView(context, viewId);
		root.setViewId(viewId);
		context.setViewRoot(root);
		// }
	}

	/**
	 * Funkcja zwracajaca drzewo taksonomii, wywolywana przez GUI aplikacji
	 */
	@Override
	public TreeNodeImpl<TaxElement> getRootNode() {
		if (rootNode == null)
			loadTree();
		return rootNode;
	}

	/**
	 * Funkcja ustawiajaca drzewo taksonomii
	 */
	@Override
	public void setRootNode(TreeNodeImpl<TaxElement> rootNode) {
		this.rootNode = rootNode;
	}

	/**
	 * Setter TaxElementDAO
	 */
	@Override
	public void setTaxElementDAO(TaxElementDAO taxElementDAO) {
		this.taxElementDAO = taxElementDAO;
	}

	/**
	 * Getter TaxElementDAO
	 */
	@Override
	public TaxElementDAO getTaxElementDAO() {
		return taxElementDAO;
	}

	/**
	 * Setter SearchResultDAO
	 */
	@Override
	public void setSearchResultDAO(SearchResultDAO searchResultDAO) {
		this.searchResultDAO = searchResultDAO;
	}

	/**
	 * Getter SearchResultDAO
	 */
	@Override
	public SearchResultDAO getSearchResultDAO() {
		return searchResultDAO;
	}

	/**
	 * Setter SelectedNode(wybranej galezi drzewa taksonomii - obsluga GUI)
	 */
	@Override
	public void setSelectedNode(TaxElement selectedNode) {
		this.selectedNode = selectedNode;
	}

	/**
	 * Getter SelectedNode(wybranej galezi drzewa taksonomii - obsluga GUI)
	 */
	@Override
	public TaxElement getSelectedNode() {
		return selectedNode;
	}

	/**
	 * Setter obecnie edytowanego konceptu
	 */
	public void setConcept(Concept concept) {
		this.concept = concept;
	}

	/**
	 * Getter obecnie edytowanego konceptu
	 */
	public Concept getConcept() {
		return concept;
	}

	/**
	 * ustaw liste zatwierdzonych kwalifikatorow obiektow
	 * 
	 * @param conceptHistory
	 *            lista zatwierdzonych kwalifikatorow obiektow
	 */
	public void setConceptHistory(List<Concept> conceptHistory) {
		this.conceptHistory = conceptHistory;
	}

	/**
	 * pobierz liste zatwierdzonych kwalifikatorow obiektow
	 * 
	 * @return lista zatwierdzonych kwalifikatorow obiektow
	 */
	public List<Concept> getConceptHistory() {
		return conceptHistory;
	}

	/**
	 * dodaje nowy koncept do kwalifikatora i sortuje liste, ustalajac tez
	 * granice pomiedzy konceptami z poszczegolnych taksonomii
	 */
	private void addConceptToQualifier(Concept newConcept) {
		int index = findParentInConceptHistory(newConcept.getId());
		if (index >= conceptHistory.size())
			conceptHistory.add(newConcept);
		else
			conceptHistory.add(index, newConcept);
		Collections.sort(conceptHistory);

		if (redOnly()) {
			PdmLog.getLogger()
					.info("walidacja 6: nie mozna wykluczyc wszystkich konceptow (wybierz co najmniej jeden dolaczony)");
			Validator.setErrorMessage(Const.VAL_MODE_6);
		}
		// conceptHistory.add(newConcept);
		// Collections.sort(conceptHistory);

		groupByTaxName();
	}

	/**
	 * grupowanie kwalifikatorow obiektow wedlug taksonomii z ktorych pochodza
	 */
	private void groupByTaxName() {
		int lastTaxId = -1;
		for (Concept c : conceptHistory) {
			if (c.getTaxonomyId() != lastTaxId)
				c.setFirstFromThisTax(true);
			else
				c.setFirstFromThisTax(false);
			lastTaxId = c.getTaxonomyId();
		}
	}

	/*
	 * niepotrzebna metoda???? r.112 -> jacek public void
	 * valueChange(ValueChangeEvent arg0) throws AbortProcessingException {
	 * PdmLog.getLogger().info("Processing value change event..."); }
	 */

	/**
	 * obsluz zdarzenie usuniecia elementu z konceptu edytowanego przez
	 * uzytkownika wersja 1 - gradient nieodwrocony
	 * 
	 * @param startingElementName
	 */
	/*
	 * public void cutConceptV1(String startingElementName) { int elementIndex =
	 * -99; for (int i = 0; i < concept.getSelectedConcept().size(); i++) { if
	 * (concept.getSelectedConcept().get(i).getData()
	 * .equals(startingElementName)) { elementIndex = i; break; } } if
	 * (elementIndex < 0) { PdmLog.getLogger().error(
	 * "niepowodzenie przyciecia konceptu - brak elementu '" +
	 * startingElementName + "' w koncepcie"); return; }
	 * 
	 * // concept.setElementFaces(ColorGradient.getInstance().standardColor); //
	 * jesli usunieto korzen - wyczysc caly koncept if (elementIndex == 0) {
	 * PdmLog.getLogger().info("creating new concept"); // odkoloruj to co bylo
	 * pokorowane
	 * concept.setElementFaces(ColorGradient.getInstance().getStandardColor());
	 * concept = new Concept(); }
	 * 
	 * // w przeciwnym wypadku usun kolejne koncepty else { while (elementIndex
	 * < selectedConcept.size()) { // zmien kolor usuwanego elementu na standard
	 * selectedConcept.get(elementIndex).setFace(
	 * ColorGradient.getInstance().getStandardColor());
	 * selectedConcept.remove(elementIndex); }
	 * 
	 * concept.setSelectedConcept(selectedConcept); selectedNode =
	 * selectedConcept.get(elementIndex - 1);
	 * extractConceptChildren(selectedNode);
	 * 
	 * // przekoloruj gradient pozostalych konceptow (bo na pewno zostal //
	 * uciety koniec gradientu przy usuwaniu elementu) boolean
	 * onlyStandardColorLeft = true;
	 * 
	 * for (int i = 0; i < concept.getSelectedConcept().size() - 1; i++) { if
	 * (!concept.getSelectedConcept().get(i).getFace()
	 * .equals(ColorGradient.getInstance().getStandardColor())) {
	 * recolour(concept.getSelectedConcept().get(i).toString());
	 * onlyStandardColorLeft = false; break; } }
	 * 
	 * // jesli okazalo sie ze zostaly tylko koncepty w kolorze standard to //
	 * trzeba cos pokolorowac na kolor neutralny if (onlyStandardColorLeft)
	 * recolour
	 * (concept.getSelectedConcept().get(concept.getSelectedConcept().size() -
	 * 1).toString()); } }
	 */

	/**
	 * poszukuje w liscie zatwierdzonych kwalifikatorow obiektu ktory jest
	 * najblizszym rodzicem elementu o identyfikatorze 'id'
	 * 
	 * @param id
	 *            identyfikator konceptu ktoego rodzica szukam
	 */
	private int findParentInConceptHistory(String id) {
		// int bestResultIndex = -1;
		String tmpId = id;

		// poszukaj czy sa juz rodzice
		while (tmpId.contains(".")) {
			tmpId = tmpId.substring(0, tmpId.lastIndexOf("."));
			for (Concept c : conceptHistory) {
				if (c.getId().equals(tmpId)) {
					int returnIndex = conceptHistory.indexOf(c) + 1;
					return returnIndex;
				}
			}
		}

		// poszukaj czy sa juz dzieci
		for (Concept c : conceptHistory) {
			if (c.getId().contains(id)) {
				int returnIndex = conceptHistory.indexOf(c);
				return returnIndex;
			}
		}

		return 0;
	}

	/**
	 * obsluz zdarzenie usuniecia elementu z konceptu edytowanego przez
	 * uzytkownika wersja 2 - gradient odwrocony
	 * 
	 * @param startingElementName
	 */
	public void cutConceptV2(String startingElementName) {
		// znajdz usuwany TaxElement
		TaxElement element = findAdequateTaxElement(startingElementName);
		if (element == null) {
			PdmLog.getLogger().error("nie znaleziono elementu w koncepcie");
			return;
		}

		// znajdz indeks usuwanego elementu w koncepcie
		int elementIndex = findElementIndex(element);
		if (elementIndex < 0) {
			PdmLog.getLogger().error(
					"niepowodzenie przyciecia konceptu - brak elementu '"
							+ startingElementName + "' w koncepcie");
			return;
		}

		// jesli wybrano do usuniecia korzen - wyczysc caly koncept
		if (elementIndex == 0) {
			PdmLog.getLogger().info("Usunieto korzen - Tworze nowy koncept");
			clearCurrentConcept();
		}

		// w przeciwnym wypadku usun kolejne koncepty
		else {
			while (elementIndex < concept.getSelectedConcept().size()) {
				// zmien kolor usuwanego elementu na standard
				concept.getSelectedConcept()
						.get(elementIndex)
						.setFace(ColorGradient.getInstance().getStandardColor());
				// zresetuj abstractionIndex
				concept.getSelectedConcept().get(elementIndex)
						.setAbstractionIndex(-1);
				// usun ten element
				concept.getSelectedConcept().remove(elementIndex);
			}

			// pobierz dzieci obecnego konceptu
			extractConceptChildren(concept.getSelectedConcept().get(
					concept.getSelectedConcept().size() - 1));

			// selectedConcept = concept.getSelectedConcept();
			selectedNode = findSelectedNode();

			// uaktualnij abstractionIndexy
			updateAbstractionIndexes(selectedNode);
		}
	}

	/**
	 * metoda usuwa obecnie edytowany koncept (ten ktory znajduje sie w polu
	 * edycji)
	 */
	private void clearCurrentConcept() {
		// odkoloruj to co bylo pokorowane
		concept.setElementFaces(ColorGradient.getInstance().getStandardColor());
		// zresetuj indeksy
		concept.resetAbstractionIndexes();
		concept = new Concept();
	}

	/**
	 * znajduje TaxElement, ktory zostal wybrany przez uzytkownika. Robi to na
	 * bazie sprawdzania wartosci abstractionIndex. Uzywana glownie gdy
	 * edytowany koncept zostal uciety i nalezy oznaczyc elementy na nowo za
	 * pomoca zmiennych abstracionIndex (kolorowanie)
	 * 
	 * @return element ktory zostal uznany przez algorytm jako koniec
	 *         specyficzny
	 */
	public TaxElement findSelectedNode() {
		TaxElement specificEnd = new TaxElement();
		boolean elementFound = false;

		// znajdz czy ktorys z elementow nie ma juz ustawionego abstractionIndex
		for (TaxElement te : concept.getSelectedConcept()) {
			if (te.getAbstractionIndex() != -1) {
				specificEnd = te;
				elementFound = true;
				break;
			}
		}

		// jesli wszystkie elementy maja zresetowane abstractionIndex to wybierz
		// ostatni z listy
		if (!elementFound)
			specificEnd = concept.getSelectedConcept().get(
					concept.getSelectedConcept().size() - 1);

		return specificEnd;
	}

	/**
	 * znajduje element w aktualnie edytowanym koncepcie i zwraca jego index
	 * (ktory w kolejnosci jest ten element w koncepcie)
	 * 
	 * @param element
	 *            TaxElement do wyszukania
	 * @return index w aktualnym kocepcie. zwraca '-1' gdy elementu nie ma w
	 *         koncepcie.
	 */
	private int findElementIndex(TaxElement element) {
		for (int i = 0; i < concept.getSelectedConcept().size(); i++) {
			if (concept.getSelectedConcept().get(i).equals(element)) {
				return i;
			}
		}
		PdmLog.getLogger().error("nie znaleziono elementu w koncepcie");
		return -1;
	}

	/**
	 * Setter indexingMode(flaga rozrozniajaca tryb szukania i indexowania)
	 * 
	 * @param indexingMode
	 */
	public void setIndexingMode(boolean indexingMode) {
		this.indexingMode = indexingMode;
	}

	/**
	 * Getter indexingMode(flaga rozrozniajaca tryb szukania i indexowania)
	 * 
	 * @param indexingMode
	 */
	public boolean isIndexingMode() {
		return indexingMode;
	}

	/**
	 * Funkcja przechodzenia miedzy trybem indexowania a szukania(i na odwrot)
	 * wraz z odpowiednimi dzialaniami
	 */
	public void changeMode() {
		indexingMode = !indexingMode;
		String tmp;
		if (indexingMode)
			tmp = "indexing";
		else
			tmp = "normal";
		reset();
		PdmLog.getLogger().info("changing mode to:" + tmp);
	}

	/**
	 * Setter wektora przechowujacego wybrany koncept taksonomii w trybie
	 * indeksowania
	 * 
	 * @param selectedTaxElements
	 */
	public void setSelectedTaxElements(List<TaxElement> selectedTaxElements) {
		this.selectedTaxElements = selectedTaxElements;
	}

	/**
	 * Getter wektora przechowujacego wybrany koncept taksonomii w trybie
	 * indeksowania
	 * 
	 * @param selectedTaxElements
	 */
	public List<TaxElement> getSelectedTaxElements() {
		if (selectedTaxElements == null)
			selectedTaxElements = new ArrayList<TaxElement>();
		return selectedTaxElements;
	}

	/**
	 * Funkcja usuwajaca koncept ze zbioru wybranych taksonomii - tryb
	 * indeksowania
	 * 
	 * @param id
	 * @return
	 */
	public boolean removeFromSelectedTaxElements(Integer id) {
		if (selectedTaxElements != null)
			for (int i = 0; i < selectedTaxElements.size(); i++) {
				if (selectedTaxElements.get(i).getId().equals(id)) {
					selectedTaxElements.get(i).setSelected(false);
					selectedTaxElements.get(i).setColor(null);
					selectedTaxElements.remove(i);
					return true;
				}
			}
		return false;
	}

	/**
	 * Setter zmiennej przechowujacej element dodawany w trybie indeksowania
	 */
	public void setAddedElement(SearchResult addedElement) {
		this.addedElement = addedElement;
	}

	/**
	 * Getter zmiennej przechowujacej element dodawany w trybie indeksowania
	 */
	public SearchResult getAddedElement() {
		if (addedElement == null)
			addedElement = new SearchResult();

		return addedElement;
	}

	/**
	 * Setter FileDAO
	 * 
	 * @param fileDAO
	 */
	public void setFileDAO(FileDAO fileDAO) {
		this.fileDAO = fileDAO;
	}

	/**
	 * Getter FileDAO
	 * 
	 * @return
	 */
	public FileDAO getFileDAO() {
		return fileDAO;
	}

	/**
	 * Funkcja, ktora bedzie uzywana przy przejsciu z indexing mode do zwyklego
	 * i z powrotem. sluzy do czyszczenia zaznaczen elementow z trybu
	 * indeksowania/szukania
	 */
	public boolean reset() {
		try {
			// czyszczenie zaznaczen z indexing
			if (selectedTaxElements != null) {
				for (int i = 0; i < selectedTaxElements.size(); i++) {
					selectedTaxElements.get(i).setColor(null);
					selectedTaxElements.get(i).setSelected(false);
					selectedTaxElements.get(i).setFace(null);
				}
				selectedTaxElements.clear();
			}

			setAddedElement(null);

			// czyszczenie zaznaczen z wyszukiwania
			clearCurrentConcept();
			resultsNeedsToBeRefreshed = true;
			intervalResultsNeedsToBeRefreshed = true;

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * obsuga zdarzenia edycji konceptu w przestrzei roboczej, czyli
	 * rozszerzenia konceptu do abstrakcyjnego i odwrotnie - wcisniecie
	 * przycisku (TaxElementu) przez uzytkownika
	 * 
	 * @param elementName
	 *            nazwa kliknietego TaxElementu
	 */
	public void conceptEditing(String elementName) {
		// odnajdz tax element ktory zostal klikniety
		TaxElement selectedElement = findAdequateTaxElement(elementName);

		// zaktualizuj indeksy
		if (selectedElement != null)
			updateAbstractionIndexes(selectedElement);
		else
			PdmLog.getLogger()
					.error("nie udalo sie odnalezc kliknietego elementu w edytowanym koncepcie");
	}

	/**
	 * przeszukuje aktualnie edytowany koncept w celu znalezienia TaxElementu o
	 * nazwie elementNAmeToFind
	 * 
	 * @param elementNameToFind
	 *            nazwa szukanego elementu
	 * @return znaleziony TaxElement o nazwie elementNameToFind
	 */
	public TaxElement findAdequateTaxElement(String elementNameToFind) {
		for (TaxElement te : concept.getSelectedConcept()) {
			if (te.getData().equals(elementNameToFind))
				return te;
		}
		return null;
	}

	/**
	 * rozpoczecie nowego wyszukiwania - czyszczenie listy konceptow
	 */
	public void newSearch() {
		try{
			PdmLog.getLogger().info("Nowe wyszukiwanie");
			conceptHistory = new ArrayList<Concept>();
			searchResultVector.clear();
			intervalSearchResultVector.clear();
		}catch(Exception e){
			PdmLog.getLogger().info("Nowe wyszukiwanie - Obsluzony wyjatek: " + e.getMessage());
		}
	}

}