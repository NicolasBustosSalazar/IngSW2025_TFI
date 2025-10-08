# 🏥 Sistema de Gestión de Prioridad de Triaje — TFI Ingeniería de Software (UTN FRT)

Este proyecto corresponde al **Trabajo Final de Ingeniería de Software** de la carrera **Ingeniería en Sistemas de Información** (UTN – Facultad Regional Tucumán).  
El sistema tiene como objetivo **gestionar y asignar prioridades de atención a pacientes en el área de triaje de una guardia hospitalaria**, optimizando el flujo de atención y reduciendo los tiempos de espera mediante criterios estandarizados.

---

## 🎯 Objetivos del proyecto

- Desarrollar un sistema que permita registrar pacientes y asignar niveles de prioridad según criterios de triaje.  
- Implementar una arquitectura modular en **Java**, promoviendo la mantenibilidad y escalabilidad del software.  
- Aplicar un enfoque de **Desarrollo Guiado por el Comportamiento (BDD)** para garantizar la correcta especificación y validación de los requisitos.  
- Documentar y aplicar buenas prácticas de ingeniería de software a lo largo del ciclo de vida del proyecto.  

---

## ⚙️ Tecnologías utilizadas

- **Lenguaje:** Java  
- **Paradigma:** Orientado a Objetos  
- **Metodología:** BDD (Behavior Driven Development)  
- **Frameworks / Herramientas:**  
  - Cucumber (para definición de escenarios BDD)  
  - JUnit (para pruebas automatizadas)  
  - Maven (para gestión de dependencias)  
  - IntelliJ IDEA / Eclipse (entorno de desarrollo)  
  - GitHub (control de versiones)

---

## 🏗️ Arquitectura del sistema

El sistema sigue una arquitectura modular que separa las responsabilidades principales en capas:

- **Capa de Presentación:** interfaz gráfica o CLI para la interacción con el usuario.  
- **Capa de Negocio:** lógica para la asignación de prioridad, gestión de pacientes y reglas de triaje.  
- **Capa de Datos:** almacenamiento y persistencia de la información de pacientes y registros de atención.  
- **Capa de Pruebas (BDD):** escenarios definidos en Gherkin, implementados con Cucumber y JUnit.  

---

## 🚀 Ejecución del proyecto

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/usuario/repositorio-triaje.git
