PGDMP                          o            test    9.0.1    9.0.1                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false                       0    0 
   STDSTRINGS 
   STDSTRINGS     )   SET standard_conforming_strings = 'off';
                       false            �           1259    33042    curve    TABLE     �   CREATE TABLE curve (
    id integer NOT NULL,
    origin integer,
    fin integer,
    value integer,
    type character varying(255)
);
    DROP TABLE test.curve;
       test         sa    false    7                      0    33042    curve 
   TABLE DATA               6   COPY curve (id, origin, fin, value, type) FROM stdin;
    test       sa    false    1511   �                  2606    33046 
   curve_pkey 
   CONSTRAINT     G   ALTER TABLE ONLY curve
    ADD CONSTRAINT curve_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY test.curve DROP CONSTRAINT curve_pkey;
       test         sa    false    1511    1511                       2606    33082    fk3d808cf8f030739    FK CONSTRAINT     f   ALTER TABLE ONLY curve
    ADD CONSTRAINT fk3d808cf8f030739 FOREIGN KEY (origin) REFERENCES city(id);
 ?   ALTER TABLE ONLY test.curve DROP CONSTRAINT fk3d808cf8f030739;
       test       sa    false    1511    1510                       2606    33077    fk3d808cfcb22e41e    FK CONSTRAINT     c   ALTER TABLE ONLY curve
    ADD CONSTRAINT fk3d808cfcb22e41e FOREIGN KEY (fin) REFERENCES city(id);
 ?   ALTER TABLE ONLY test.curve DROP CONSTRAINT fk3d808cfcb22e41e;
       test       sa    false    1510    1511               �  x�UVK�1\���H��Q�,��lr�s�
ۍ����1���$I�T4}�������G�9��ן?� %���c��@Z*-�-iF�$���ѓ��z ��]�&R#�,o*8,�Fd���K7t��Z�«jIz/�^Wx�S)=�Ix@��Qī�p�DFe�@dh@�ݫ�-�z*�|!4�����Ҷ�|e�Xv����_��(Hꌋ�j�	t�@B5j,�����E��z7�؝��jK}�W[Ht�P���zZP�X&��r,/hĥA�T�G�FE �E�V6�6v�z̉�� a����$��<&C�����j	(���b[d��1��Ղ�3��~���>��rQx��ۚ�TYr����Dh�E�471�c�lnr ��pG*M�C������zdƊ�����]0�4ϋ���y;��&y���l4��畼0�\�wL}���hNc�@���q=�y,�&|�G�vd\ay�;G��N�I��D}>�I������r��}K%����b��	�a��\�a�����JW�b=^'�9����$�1�0��0��O�1=�p�p��+�x���(�H�U>ſ���Wu�~Tu��<L�0d���c�,�ʮ�sk��Ч�������5��Q������^1�Q�%F	��X��6��i��z�b�}��6�ѥ�Gߒ���KJv�	�.#ٌ�,[V�ђ-�����^l���՛e���A�m͵���W��J`w�~fU���_���v���     