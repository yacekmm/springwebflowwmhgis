PGDMP     4    3                 o            test    9.0.1    9.0.1                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false                       0    0 
   STDSTRINGS 
   STDSTRINGS     )   SET standard_conforming_strings = 'off';
                       false                        2615    33030    test    SCHEMA        CREATE SCHEMA test;
    DROP SCHEMA test;
             sa    false            �           1259    33032    city    TABLE     P   CREATE TABLE city (
    id integer NOT NULL,
    name character varying(255)
);
    DROP TABLE test.city;
       test         sa    false    7            �           1259    41036 
   connection    TABLE     �   CREATE TABLE connection (
    id integer NOT NULL,
    destination integer,
    source integer,
    weight integer,
    status boolean,
    idx integer
);
    DROP TABLE test.connection;
       test         sa    false    7            �           1259    33042    curve    TABLE     �   CREATE TABLE curve (
    id integer NOT NULL,
    origin integer,
    fin integer,
    value integer,
    type character varying(255)
);
    DROP TABLE test.curve;
       test         sa    false    7            �           1259    41046    networkgraph    TABLE     7   CREATE TABLE networkgraph (
    id integer NOT NULL
);
    DROP TABLE test.networkgraph;
       test         sa    false    7            �           1259    41051    switch    TABLE     c   CREATE TABLE switch (
    id integer NOT NULL,
    name character varying(255),
    idx integer
);
    DROP TABLE test.switch;
       test         sa    false    7                      0    33032    city 
   TABLE DATA               !   COPY city (id, name) FROM stdin;
    test       sa    false    1508   �                 0    41036 
   connection 
   TABLE DATA               K   COPY connection (id, destination, source, weight, status, idx) FROM stdin;
    test       sa    false    1510   ?                 0    33042    curve 
   TABLE DATA               6   COPY curve (id, origin, fin, value, type) FROM stdin;
    test       sa    false    1509   \                 0    41046    networkgraph 
   TABLE DATA               #   COPY networkgraph (id) FROM stdin;
    test       sa    false    1511   �                 0    41051    switch 
   TABLE DATA               (   COPY switch (id, name, idx) FROM stdin;
    test       sa    false    1512   �       �           2606    33036 	   city_pkey 
   CONSTRAINT     E   ALTER TABLE ONLY city
    ADD CONSTRAINT city_pkey PRIMARY KEY (id);
 6   ALTER TABLE ONLY test.city DROP CONSTRAINT city_pkey;
       test         sa    false    1508    1508                       2606    41040    connection_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY connection
    ADD CONSTRAINT connection_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY test.connection DROP CONSTRAINT connection_pkey;
       test         sa    false    1510    1510                       2606    33046 
   curve_pkey 
   CONSTRAINT     G   ALTER TABLE ONLY curve
    ADD CONSTRAINT curve_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY test.curve DROP CONSTRAINT curve_pkey;
       test         sa    false    1509    1509                       2606    41050    networkgraph_pkey 
   CONSTRAINT     U   ALTER TABLE ONLY networkgraph
    ADD CONSTRAINT networkgraph_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY test.networkgraph DROP CONSTRAINT networkgraph_pkey;
       test         sa    false    1511    1511                       2606    41055    switch_pkey 
   CONSTRAINT     I   ALTER TABLE ONLY switch
    ADD CONSTRAINT switch_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY test.switch DROP CONSTRAINT switch_pkey;
       test         sa    false    1512    1512            	           2606    33082    fk3d808cf8f030739    FK CONSTRAINT     f   ALTER TABLE ONLY curve
    ADD CONSTRAINT fk3d808cf8f030739 FOREIGN KEY (origin) REFERENCES city(id);
 ?   ALTER TABLE ONLY test.curve DROP CONSTRAINT fk3d808cf8f030739;
       test       sa    false    1508    1790    1509                       2606    33077    fk3d808cfcb22e41e    FK CONSTRAINT     c   ALTER TABLE ONLY curve
    ADD CONSTRAINT fk3d808cfcb22e41e FOREIGN KEY (fin) REFERENCES city(id);
 ?   ALTER TABLE ONLY test.curve DROP CONSTRAINT fk3d808cfcb22e41e;
       test       sa    false    1508    1790    1509                       2606    41071    fk928eab34748c9162    FK CONSTRAINT     l   ALTER TABLE ONLY switch
    ADD CONSTRAINT fk928eab34748c9162 FOREIGN KEY (id) REFERENCES networkgraph(id);
 A   ALTER TABLE ONLY test.switch DROP CONSTRAINT fk928eab34748c9162;
       test       sa    false    1511    1512    1796                       2606    41066    fkeeae6ade1b038d09    FK CONSTRAINT     s   ALTER TABLE ONLY connection
    ADD CONSTRAINT fkeeae6ade1b038d09 FOREIGN KEY (destination) REFERENCES switch(id);
 E   ALTER TABLE ONLY test.connection DROP CONSTRAINT fkeeae6ade1b038d09;
       test       sa    false    1798    1512    1510                       2606    41061    fkeeae6ade3acdb116    FK CONSTRAINT     n   ALTER TABLE ONLY connection
    ADD CONSTRAINT fkeeae6ade3acdb116 FOREIGN KEY (source) REFERENCES switch(id);
 E   ALTER TABLE ONLY test.connection DROP CONSTRAINT fkeeae6ade3acdb116;
       test       sa    false    1798    1510    1512            
           2606    41056    fkeeae6ade748c9162    FK CONSTRAINT     p   ALTER TABLE ONLY connection
    ADD CONSTRAINT fkeeae6ade748c9162 FOREIGN KEY (id) REFERENCES networkgraph(id);
 E   ALTER TABLE ONLY test.connection DROP CONSTRAINT fkeeae6ade748c9162;
       test       sa    false    1510    1796    1511               o   x�3���)�*���2�O,*�J,O�2�t�L<�TY\���e��S�����e������e�静����e�y���攣��,8��R��o.��I,��8���A�=... �1$�            x������ � �         c  x�U�1ND1Dk�aP�؉S"!����&��'�����3�DE�������u�x�ك�H�~~���T1�K�(5Ҥ6���Z�H�h?D�0~�k4�%�i+[��6�(�!���TP�*ߪc|����a�H�J �҉�b^p���*7ڬ
F҈fB���*P���9��
�{e_�*��4cU��їa4itY��	4�0aƌ�:*�3�<�^(����MzR�a8�xtO�57b�:�����P��peV�3���YΌ^7b�>�¬�w�PЄ7 ��C�R��9�/�hlv��n���b>��aㆰ͔�EŇC����Kӧ�"���1x[�6i���v����}            x������ � �            x������ � �     