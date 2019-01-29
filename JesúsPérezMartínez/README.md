<img src="images/icon.png" align="right" height="166" width="200"/>

# PaleoSW: fósiles y yacimientos paleontológicos en la web semántica 
 Trabajo final de la asignatura Web Semántica y Datos Enlazados.
 
 Realizado por: Jesús Pérez Martínez.
 
 Curso 2018-2019
 
## 1. Intoducción
El presente trabajo pretende abordar y llevar a la práctica los conocimientos adquiridos en la asignatura "Web Semántica y Datos enlazados" del Máster en Investigación en Inteligencia Artificial. La Web semántica busca cambiar el modelo Web actual, añadiendo significado a la información mostrada además de enlazar las relaciones entre los diferentes conceptos. Actualmente, existen multitud de sistemas y aplicaciones que usan los estándares y tecnologías definidas y desarrolladas al amparo del World Wide Web Consortium (W3C). Estas tecnologías destacan por su gran utilidad, transparencia y facilidad de reutilización en gran cantidad de proyectos y aplicaciones.

Por este motivo, tomando como punto de partida una porción del conjunto de datos total que podemos encontrar en <a href="https://paleobiodb.org/">The Paleobiology Database</a>, trataremos de traducir de una manera clara y versátil el conjunto de datos inicial (en formato *.csv*), a un grafo RDF. Este grafo, junto con el uso de una ontología, permitirá el enlazado de los conceptos de nuestra base de datos con los ya existentes en la Web. Por último, se creará una pequeña aplicación que permitirá la explotación del grafo para extraer información de utilidad.

## 2. Proceso de transformación
A lo largo de los siguientes puntos, se describirán cada uno de los apartados principales, desde la elección de la base de datos hasta su publicación. Así pues, comenzaremos explicando el contexto y las características del *dataset* y su licencia, para posteriormente analizarlo y adaptarlo a nuestra idea de grafo final. Proseguiremos definiendo la estrategia de nombrado de recursos y el desarrollo del vocabulario, donde se creará la ontología que usaremos en la transformación de la base de datos al grafo RDF.

Tras finalizar este bloque, se estudiará y definirá el enlazado de los recursos de nuestro grafo con los encontrados en la Web antes de realizar la publicación correspondiente.

### 2.1. Selección de la fuente de datos
La fuente de datos que usaremos como base es una porción de The Paleobiology Database (en adelante **PBDB**), correspondiente a los hayazgos encontrados en la mitad este de la Península Ibérica y las Islas Baleares. PBDB es una base de datos pública de información paleontológica abierta, mantenida por un grupo internacional no gubernamental de paleontólogos. Permite explorar los datos desde el navegador y descargarlos para su posterior análisis. Toda la información que contiene esta plataforma se encuentra bajo la licencia de Creative Commons **CC BY 4.0 International License**, que permite distribuir y modificar el contenido siempre que se atribuya la autoría del mismo.

<p align="center">
<img src="images/pbdb_black.png" height="350" width="430"/>
</p>

Personalmente, me ha parecido muy interesante usar dicha base de datos en este trabajo, dado que no existe información clara y precisa en la Web semántica actual sobre los datos que en este *dataset* se abordan. El conjunto de datos descargado contiene **1842 registros** y ha sido obtenido del original (con 1371645 registros totales) aplicando los siguientes filtros que nos permite la <a href="https://paleobiodb.org/navigator/">aplicación web</a>:
* **Map extent**: ([38, -7], [42, 4])
* **Interval**: Mesozoic
* **Taxon**: Chordata

Tal y como se indica, sólo hemos elegido aquellos registros fósiles de vertebrados (*Chordata*) que pertenecen a la época de los dinosaurios (Mesozoico).

### 2.2. Análisis de datos
#### Licencias
Comenzaremos este punto tratando más en detalle la licencia de los datos originales. Como hemos comentado previamente, se trata de una licencia <a href="https://creativecommons.org/licenses/by/4.0/">CC BY 4.0 International License</a>, cuyo icono aparece representado a continuación:

<p align="center">
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/"><img alt="Licencia de Creative Commons" style="border-width:0" src="https://i.creativecommons.org/l/by/4.0/88x31.png" /></a>
 </p>
 
 Esta licencia, según la web oficial de Creative Commons, tiene las siguientes implicaciones:
 * **Libertad de**:
     * Compartir: copiar y redistribuir el material en cualquier medio o formato.
     * Adaptar: remezclar, transformar y construir sobre el material para cualquier propósito, incluso comercialmente.
 * **Deber de**:
     * Reconocimiento: debe otorgar el crédito correspondiente, proporcionar un enlace a la licencia e indicar si se realizaron cambios. Puede hacerlo de cualquier manera razonable, pero no de una manera que sugiera que tiene el apoyo del licenciador o lo recibe por el uso que hace.
     
Por lo tanto, usaremos la misma licencia para los datos transformados que se generen a partir de este proyecto.

Ahora pasaremos a analizar el conjunto de datos haciendo uso de la herramienta *OpenRefine*. Primero cargamos el *dataset* con las siguientes opciones proporcionadas por el programa:

* Character encoding: 	UTF-8
* Columns are separated by commas (CSV)
* Ignore first 18 lines at begining

#### Columnas eliminadas

Tras esto, ya podemos trabajar con el *dataset*. Si estudiamos las *facets* generadas sobre cada una de las columnas, descubriremos que la gran mayoría de las mismas no nos van a ser útiles para nuestro grafo final, y por lo tanto las eliminaremos de nuestro *dataset*. A continuación, se muestran todas las columnas eliminadas y su correspondiente motivo:

| Nombre de la columna | Motivo |
|---------|---------|
|flags | Información interna de la aplicación |
| record_type | Información interna de la aplicación |
| reid_no | Información interna de la aplicación |
| difference | 100% valores nulos |
| accepted_attr | Información interna de la aplicación |
| accepted_rank | Duplicado con identified_rank |
| late_interval | Eliminado por el gran número de valores nulos (1366/1842 = **75%**) |
| latlng_basis | Información interna de la aplicación |
| geogscale | Información interna de la aplicación |
| geogcomments | Información interna de la aplicación |
| protected | 100% valores nulos |
| cx_int_no | Información interna de la aplicación |
| stratgroup | Eliminado por el gran número de valores nulos (1225/1842 = **67%**) |
| member | Información interna de la aplicación |
| zone | Eliminado por el gran número de valores nulos (1804/1842 = **98%**) |
| localbed | Eliminado por pertenecer a detalles demasiado específicos del yacimiento + cantidad de valores nulos (1501/1842 = **82%** ) |
| localorder | Eliminado por pertenecer a detalles demasiado específicos del yacimiento + cantidad de valores nulos (1503/1842 = **82%** ) |
| regionalbed | 100% valores nulos |
| regionalorder | 100% valores nulos |
| regionalsection | Eliminado por el gran número de valores nulos (1841/1842 = **99.95%**) |
| stratcomments | Anotaciones del estrato |
| lithdescript | Anotaciones adicionales del estrato |
| lithadj1 | Anotaciones adicionales sobre la litología |
| fossilsfrom1 | Información interna de la aplicación |
| lithification1 | Eliminado por el gran número de valores nulos (1666/1842 = **91%**) |
| minor_lithology1 | Eliminado por el gran número de valores nulos (1494/1842 = **81%**) |
| lithology2 | Eliminado por el gran número de valores nulos (1255/1842 = **68%**) |
| lithadj2 | Anotaciones adicionales sobre la litología + eliminado por el gran número de valores nulos (1667/1842 = **91%**) |
| lithification1 | Eliminado por el gran número de valores nulos (1841/1842 = **99.95%**) |
| minor_lithology2 | Eliminado por el gran número de valores nulos (1765/1842 = **96%**) |
| fossilsfrom2 | Información interna de la aplicación |
| tectonic_setting | Eliminado por el gran número de valores nulos (1751/1842 = **95%**) |
| geology_comments | Anotaciones sobre la geología del yacimiento |
| occurrence_comments | Anotaciones sobre el hayazgo |
| created | Información interna de la aplicación |
| modified | Información interna de la aplicación |
| paleomodel | Información interna de la aplicación |
| geoplate | Información interna de la aplicación |
| paleolng | Información interna de la aplicación |
| paleolat | Información interna de la aplicación |

Tras eliminar todas las columnas anteriores, nuestro *dataset* se quedará con 28 columnas, sobre las que tendremos que realizar ciertos retoques para evitar errores en los valores. Esto se conseguirá, de nuevo, haciendo uso de las *facets* (para texto) que nos permite *OpenRefine*.

#### Valores renombrados
Debido a la eliminación de *lithology2* y las columnas relacionadas que dan información adicional de la litología, renombraremos la columna *lithology1* a *main_lithology*, con el fin de usar en un futuro únicamente el valor principal de este campo y evitar posibles errores de claridad y concisión.

Si hacemos un *facet* sobre la columna *state*, observamos que también hay unos cuantos valores mal escritos, que no se corresponden con nombres reales. Estos valores y sus modificaciones son los siguientes:

| Ocurrencia(s) | Modificado por |
|--------------|-----------------|
| Catalonia, Cataluna | Cataluña |
| Castilla y Leon, Castilla y Léon | Castilla y León |
| Aragon | Aragón |
| Tereul | Teruel |
| Valenciana | Valencia |

También encontramos diferentes cadenas de caracteres que indican un mismo valor, o que están escritos en un formato diferente al resto, dentro de la columna *environment* y *main_lithology*. En ambas columnas encontramos algunos valores entre comillas, cuando en cambio la gran mayoría de los valores con los que comparten columna no tienen esta característica de formato. A continuación vemos las modificaciones para la columna *environment*:

| Ocurrencia | Modificado por |
|--------------|-----------------|
| "channel" | channel |
| "floodplain" | floodplain |

Y para la columna *main_lithology*:

| Ocurrencia | Modificado por |
|--------------|-----------------|
| "claystone" | claystone |
| "limestone" | limestone |
| "shale" | shale |
| "siliciclastic" | siliciclastic |

#### Análisis del *dataset* final

Para concluir este punto, representaremos más en detalle el significado y algunos datos de interés para la futura transformación y explotación de las 28 columnas que componen nuestro conjunto de datos final. Las descripciones de las columnas se han obtenido desde la <a href="https://paleobiodb.org/data1.2/">documentación oficial de PBDB</a>. 

| Nombre de la columna | Tipo | Rango | Breve descripción |
|----------------------|------|-------|-------------------|
| occurrence_no | String |  |  ID que representa la existencia de un organismo particular en un espacio y tiempo determinados. Valor único para cada hayazgo |
| collection_no | String | | ID que representa un conjunto de hayazgos que están ubicados en el mismo tiempo y lugar geográfico |
| identified_name | String | | Nombre con el que se ha identificado al organismo |
| identified_rank | String | 14 posibles valores | Categoría taxonómica a la que pertenece el hayazgo |
| identified_no | String |  | ID del nombre con el que se ha identificado al organismo |
| accepted_name | String |  | Nombre final que se le ha dado al organismo |
| accepted_no | String |  | ID del nombre final que se le ha dado al organismo. Puede coincidir con *identified_no* |
| early_interval | String | 44 posibles valores | Intervalo geológico inicial del hayazgo, en base a los estándares proporcionados por la <a href="http://www.stratigraphy.org/">Comisión Internacional de Estratigrafía</a> |
| max_ma | Number | 70.00 - 260.00 | Edad máxima del hayazgo (en millones de años) |
| min_ma | Number | 60.00 - 250.00 | Edad mínima del hayazgo (en millones de años) |
| reference_no | String |  |  |
| lng | Number | (-7) - 4 | Coordenada angular de longitud del hayazgo (aproximada) |
| lat | Number | 38 - 42 | Coordenada angular de latitud del hayazgo (aproximada) |
| cc | String | Valor único (ES) | Country code (Código de país) |
| state | String | 19 posibles valores | Provincia / CCAA |
| county | String | 45 posibles valores | Municipio |
| latlng_precision | String | 10 posibles valores | Precisión del punto geográfico marcado respecto al valor real |
| formation | String | 58 posibles valores | Formación geológica del hayazgo |
| stratscale | String | 5 posibles valores |  | 
| localsection | String | 21 posibles valores | Nombre del yacimiento |
| lithology_main | String | 18 posibles valores | Compuesto mineral principal |
| Environment | String | 34 posibles valores | Tipo de entorno en el que se encuentra el hayazgo |
| authorizer_no | String |  | ID de la persona encargada de autorizar añadir el hayazgo en la base de datos |
| enterer_no | String |  | ID de la persona encargada de añadir el hayazgo en la base de datos |
| modifier_no | String |  | ID de la persona encargada de modificar el hayazgo en la base de datos |
| authorizer | String | 15 posibles valores | Nombre de la persona encargada de autorizar añadir el hayazgo en la base de datos |
| enterer | String | 28 posibles valores | Nombre de la persona encargada de autorizar añadir el hayazgo en la base de datos |
| modifier | String | 14 posibles valores | Nombre de la persona encargada de autorizar añadir el hayazgo en la base de datos |

**NOTA: ES POSIBLE QUE LOS DATOS PROPORCIONADOS EN ESTE PUNTO SEAN ELIMINADOS O MODIFICADOS DEPENDIENDO DEL DESARROLLO DEL TRABAJO** 

### 2.3. Estrategia de nombrado
### 2.4. Desarrollo del vocabulario

Ontologías base:
* Taxones: http://lod.taxonconcept.org/ontology/taxon.owl#Taxon
* Era geológica y ¿formación estrato?: http://resource.geosciml.org/ontology/timescale/gts#GeologicTimescale
* Litología: http://dati.isprambiente.it/ontology/core/#lithologyType
* Lugares: https://schema.org/Place
* Coordenadas: https://schema.org/GeoCoordinates
### 2.5. Proceso de transformación
### 2.6. Enlazado
### 2.7. Publicación
## 3. Aplicación y explotación
## 4. Conclusiones
## 5. Bibliografía
