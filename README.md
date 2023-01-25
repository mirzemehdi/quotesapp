# QuotesApp - MultiModular Android APP

[![Build Debug apk](https://github.com/mirzemehdi/quotesapp/actions/workflows/build_debug.yml/badge.svg)](https://github.com/mirzemehdi/quotesapp/actions/workflows/build_debug.yml)
[![Static Code Analyzing](https://github.com/mirzemehdi/quotesapp/actions/workflows/static_code_analyze.yml/badge.svg)](https://github.com/mirzemehdi/quotesapp/actions/workflows/static_code_analyze.yml)
[![Testing](https://github.com/mirzemehdi/quotesapp/actions/workflows/unit_testing.yml/badge.svg)](https://github.com/mirzemehdi/quotesapp/actions/workflows/unit_testing.yml)

This app shows quotes and is developed using **App Modularization**, **Clean Architecture** and **Jetpack Components**. 
Project is developed in **TDD (Test Driven Development) style** by writing Unit and UI tests.

## Overview of Architecture

<img src="outputs/screenshots/app_dependency_graph.png" alt="architecture" >

#### App module
All feature modules are included in app module. Main responsibility of app module is 
providing dependencies using **_Koin Dependency Injection_** and navigation between modules.

#### Feature modules
**_:feature:quotes_:**  This module is for listing and adding new quotes.  
**_:feature:profile_:**  This module is for viewing profile.

Each feature module is developed using Clean Architecture, and inside 
each module there are 3 packages (_domain, data, presentation_) for separating 
layers. Because in kotlin there is no package visibility modifier, custom **_Clean Architecture_**
**_detekt_** rule is created in **_:customdetektrules_** module, and this module is implemented
in each feature module in order not to breaking clean architecture rules. Beside that each 
feature module includes **_:common:core_** and **_:common:ui_** modules. For testing purposes
**_:test-utils_** is also included in feature modules using _testImplementation_ 
and _androidTestImplementation_.

#### Common Modules
**_:common:core_:**  This module contains core objects that can be used 
in every android library module, like _Result_ or _ErrorEntity_ classes.

**_:common:ui_:**  This module contains main styling, coloring, theming of the project. 
It also includes some UI helper classes.

_*When project grows :common:strings module can also be created for common string resources_

#### Network module
This module is responsible for initializing **_Retrofit_** and providing API services.
Because as remote service **_Firebase_** is used in the project, this module is not included in any module.


#### Test module
Contains helper classes for making testing easier. It is included in each module 
using _testImplementation_ and _androidTestImplementation_ for both **_Unit_** and **_Instrumentation Tests_**.

#### CustomDetektRules module
Custom rules for checking code styles using _**detekt**_ are created in this module. 
One custom rule that is created inside module is _Clean Architecture Rule_ which checks that 
if any violation of architecture occurred in somewhere in the code.  
Code style can be checked by running this gradle command: **./gradlew detekt**

#### Kotlin DSL
The project uses the _**Kotlin DSL**_ to make it easy to include new dependencies and versions of Android libraries and modules.
These are included inside **_buildSrc_** module. Inside buildSrc module 
common android library gradle script is also created for making gradle files of modules more readable.

## Gradle Scripts
`./gradlew ktlintFormat` - Checks kotlin code styles and format it if possible. <br>  
`./gradlew detekt` - Checks kotlin code styles and custom code styles that is created inside _customDetektRules_ module.  <br>  
`./gradlew androidTestReport` - Code coverage report for both Android and Unit tests.

*_Each time when adding new commit **pre-commit hook** script will run, which checks if there is any code 
style problem before committing it to Github. This can be skipped using **git commit --no-verify**, 
but it will run again in CI using Github actions._

