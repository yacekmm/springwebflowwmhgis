PGDMP                          o            test    9.0.1    9.0.1                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
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
       test       sa    false    1510    1511                       2606    33077    fk3d808cfcb22e41e    FK CONSTRAINT     c   ALTER TABLE ONLY curve
    ADD CONSTRAINT fk3d808cfcb22e41e FOREIGN KEY (fin) REFERENCES city(id);
 ?   ALTER TABLE ONLY test.curve DROP CONSTRAINT fk3d808cfcb22e41e;
       test       sa    false    1510    1511               c  x�U�KN1Dמà���		�@,X������$t�E����]�$**&�&/�������DG�����I��B/M,��$H�$�A����4��.M�"q����W�%�i&j��6b+�E��T%��JUl����T���m�Tu |=���_r����m�#݈��/��9P��B4$�@��p����B�؀��IU_����i}Y�2	$0a��:"���<�V(���	�UZ��.�$p�Ȟ�o�} �7'�B���K����kǼ��1|#3-�j|)�%MDB{;Tm!e8�g���I��]��;�b�'43�����2�t�L?�\�6}�\<�F\���v���%�|     