# aem-training

- **Agregar tabs a properties:** En el proyecto se agrega la tab additionalInfo en las propiedades de las paginas. En ```aem-onboarding-training-feature-cloud-version\ui.apps\src\main\content\jcr_root\apps\aemtr```

- **Leer properties de page:** En los componente additional info y additional info siblings se leen las propiedades additional info de la pagina actual y sus hermanos respectivamente

- **Usar client-libs para components:** Esto se hace en el componente additionalInfoSiblings para agregar css y javascript

- **Agregar sistem ade styles a un componente nuevo:** Primero hay que tener las clases que se van a alternar en el clientlib. Despues para habilitar el icono del pincelito hay que agregar la _cq_design_dialog como en el additionalInfoSiblings. Revisa el componente additionalInfoSiblings.
En AEM hay que crear la policy en el template donde se encuentra el componente, recuerda exportarlo ``` aem-onboarding-training-feature-cloud-version\ui.content\src\main\content\jcr_root\conf\aemtr\settings\wcm\policies\.content.xml ```. Con eso debería funcionar

- **Agregar componente configurable por defecto a un template: ** Checar el ``` aem-onboarding-training-feature-cloud-version\ui.content\src\main\content\jcr_root\conf\aemtr\settings\wcm\templates\page-content ``` En sus carpetas structura para agregarlo e inital para establecer un valor inicial.
En policies puedes agregarle las politicas de cierto componente.
En el ejemplo se tiene un experience fragment agregado debajo de titulo

- ** Extender y heredar components: ** Checar el componente banner que extiende Image. O el extendedComponentTest que extiende componentTest

- **Crear content fragments y usarlos en un componente: ** Hay que crear el modelo de content fragment y poblarlo desde AEM. El modelo se crea desde Tools > Assets > Content Fragment Models y para poblarlo hay posicionarse en alguna carpeta de Assets y darle en crear Content Fragment. Recuerda exportar tu modelo con sus contenidos desde la carpeta ```	/conf/aemtr/settings/dam/cfm```.
Para utilizarlos en un componente revisa el pokemonInfo

- **Crear experience fragments: ** Estos se crean directamente sobre AEM

- **Utilizar Servlets y peticiones:** Es la interacción entre el back y el front. Checar el pokeApi component. Hay una clase para hacerlo por path y otra por resource type que es lo recomendado

- **Querys:** 
[Documentación](http://www.aemcq5tutorials.com/tutorials/adobe-aem-cq5-tutorials/aem-query-builder/). 
[Query builder Tool](http://localhost:4502/libs/cq/search/content/querydebugger.html)
Se extendió el search component para que cuando busque entre paginas se pueda filtrar por template y por su valor de additional info
