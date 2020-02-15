-- INSERT TEST DATA IF EMPTY
INSERT INTO VEHICLE (ID, BRAND, COLOR, CREATION_DATE, MANUFACTURED_COUNTRY, MODEL, PLATE_COUNTRY, PLATE_NUMBER,
                     VEHICLE_TYPE, VIN)
SELECT ID_GEN.nextval,
       BRAND,
       COLOR,
       CREATION_DATE,
       MANUFACTURED_COUNTRY,
       MODEL,
       PLATE_COUNTRY,
       PLATE_NUMBER,
       VEHICLE_TYPE,
       VIN
FROM (SELECT 'Mazda'                as BRAND,
             'pellentesque quisque' as MODEL,
             'SUV'                  as VEHICLE_TYPE,
             'JP'                   as PLATE_COUNTRY,
             '667-EWO-9'            as PLATE_NUMBER,
             '1GYS3AEF0CR800844'    as VIN,
             'SE'                   as MANUFACTURED_COUNTRY,
             '2002-11-23 18:31:48'  as CREATION_DATE,
             'Orange'               as COLOR
      UNION ALL
      SELECT 'Volkswagen',
             'vivamus',
             'BUS',
             'CZ',
             '061-SYO-3',
             '5GADV33L55D053076',
             'RU',
             '2001-03-20 13:58:13',
             'Green'
      UNION ALL
      SELECT 'Mazda',
             'ullamcorper purus',
             'TRAILER',
             'CA',
             '964-ZYE-7',
             'WBAPK5G54BN118465',
             'PL',
             '2017-03-20 13:32:46',
             'Crimson'
      UNION ALL
      SELECT 'Tesla',
             'libero',
             'TRUCK',
             'ID',
             '523-QLQ-3',
             'WBALX3C56CD583394',
             'UA',
             '2012-07-01 22:54:23',
             'Green'
      UNION ALL
      SELECT 'VAZ',
             'nunc commodo',
             'MINIVAN',
             'AL',
             '747-ZQD-9',
             '1G6DH8E39E0794714',
             'CN',
             '2012-11-12 10:29:40',
             'Goldenrod'
      UNION ALL
      SELECT 'Toyota',
             'porta',
             'MINIVAN',
             'PT',
             '060-ZVV-8',
             '1N4AL2AP8AC325651',
             'CN',
             '2000-10-15 08:53:57',
             'Red'
      UNION ALL
      SELECT 'BMW',
             'convallis eget',
             'HATCHBACK',
             'RU',
             '544-BBK-6',
             'KNDMB5C14F6264910',
             'AM',
             '2018-01-03 09:22:25',
             'Maroon'
      UNION ALL
      SELECT 'Toyota',
             'nulla ultrices',
             'MINIVAN',
             'ZA',
             '861-AEG-1',
             'WA1EFCFS8FR538703',
             'CN',
             '2008-07-15 03:41:41',
             'Yellow'
     ) sub
WHERE NOT EXISTS(SELECT 1
                 FROM VEHICLE
                 WHERE ID = 1);

