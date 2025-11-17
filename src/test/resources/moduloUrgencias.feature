Feature: Modulo de Urgencias
  Esta feature esta relacionada al registro de ingresos de pacientes en la sala de urgencias
  respetando su nivel de prioridad y el horario de llegada.

  Background:
    Given que la siguiente enfermera esta registrada:
    |Nombre|Apellido|
    |Susana|Gimenez |


  Scenario: Ingreso del primer paciente a la lista de espera de urgencias
    Given que estan registrados los siguientes pacientes:
    |Cuil        |Apellido|Nombre | Obra Social      |
    |23-1234567-9|Nunez   |Marcelo| Subsidio de Salud|
    |27-4567890-3|Dufour  | Alexandra| Swiss Medical |
    When Ingresan a urgencias los siguientes pacientes:
    |Cuil|          Informe       |Nivel de Emergencia|Temperatura|Frecuencia Cardiaca| Frecuencia Respiratoria|Tension Arterial|
    |23-1234567-9|Le agarro dengue|Emergencia         |38         |70                 |15                      |120/80          |
    Then La lista de espera esta ordenada por cuil de la siguiente manera:
    |23-1234567-9|

  Scenario: Ingreso de un paciente de bajo nivel de emergencia y luego otro de alto nivel de emergencia
    Given que estan registrados los siguientes pacientes:
      |Cuil        |Apellido|Nombre | Obra Social      |
      |23-1234567-9|Nunez   |Marcelo| Subsidio de Salud|
      |27-4567890-3|Dufour  | Alexandra| Swiss Medical |
      |23-4567899-2|Estrella|Patricio  | Fondo de Bikini SA|
    When Ingresan a urgencias los siguientes pacientes:
      |Cuil|          Informe       |Nivel de Emergencia|Temperatura|Frecuencia Cardiaca| Frecuencia Respiratoria|Tension Arterial|
      |23-4567899-2|Le duele el ojo|Sin Urgencia        |37         |70                 |15                      |120/80          |
      |23-1234567-9|Le agarro dengue|Emergencia         |38         |70                 |15                      |120/80          |
    Then La lista de espera esta ordenada por cuil de la siguiente manera:
      |23-1234567-9|
      |23-4567899-2|


  Scenario: Ingreso un paciente sin urgencia y dos con urgencia
    Given que estan registrados los siguientes pacientes:
      |Cuil        |Apellido|Nombre | Obra Social      |
      |23-1234567-9|Nunez   |Marcelo| Subsidio de Salud|
      |27-4567890-3|Dufour  | Alexandra| Swiss Medical |
      |23-4567899-2|Estrella|Patricio  | Fondo de Bikini SA|
    When Ingresan a urgencias los siguientes pacientes:
      |Cuil|          Informe       |Nivel de Emergencia|Temperatura|Frecuencia Cardiaca| Frecuencia Respiratoria|Tension Arterial|
      |27-4567890-3|Le duele la pestana|Sin Urgencia        |37         |70                 |15                      |120/80          |
      |23-4567899-2|Le agarro neumonia|Emergencia        |37         |70                 |15                      |120/80          |
      |23-1234567-9|Le agarro dengue|Emergencia         |38         |70                 |15                      |120/80          |
    Then La lista de espera esta ordenada por cuil de la siguiente manera:
      |23-4567899-2|
      |23-1234567-9|
      |27-4567890-3|

  Scenario: Registrar ingreso con valores negativos en Frecuencia Cardíaca
    Given que estan registrados los siguientes pacientes:
    |Cuil        |Apellido|Nombre | Obra Social      |
    |23-12345678-7|Gomez Rivera|Pablo       |Swiss Medical|

    When Ingresan a urgencias los siguientes pacientes:
      |Cuil|          Informe       |Nivel de Emergencia|Temperatura|Frecuencia Cardiaca| Frecuencia Respiratoria|Tension Arterial|
      |23-12345678-7|Le duele la pestana|Sin Urgencia        |37         |-70                 |15                      |120/80          |

    Then el sistema muestra el siguiente error: "La frecuencia cardíaca no puede ser negativa"


  Scenario: Registrar ingreso con valores negativos en Frecuencia Respiratoria
    Given que estan registrados los siguientes pacientes:
      |Cuil        |Apellido|Nombre | Obra Social      |
      |23-12345678-7|Gomez Rivera|Pablo       |Swiss Medical|

    When Ingresan a urgencias los siguientes pacientes:
      |Cuil|          Informe       |Nivel de Emergencia|Temperatura|Frecuencia Cardiaca| Frecuencia Respiratoria|Tension Arterial|
      |23-12345678-7|Le duele la pestana|Sin Urgencia        |37         |70                 |-15                      |120/80          |

    Then el sistema muestra el siguiente error: "La frecuencia respiratoria no puede ser negativa"

  Scenario: Intento de ingreso de un paciente no registrado
    Given que estan registrados los siguientes pacientes:
      |Cuil        |Apellido|Nombre | Obra Social      |
      |23-1234567-9|Nunez   |Marcelo| Subsidio de Salud|
      |27-4567890-3|Dufour  | Alexandra| Swiss Medical |
      |23-4567899-2|Estrella|Patricio  | Fondo de Bikini SA|
    When Ingresan a urgencias los siguientes pacientes:
      |Cuil|          Informe       |Nivel de Emergencia|Temperatura|Frecuencia Cardiaca| Frecuencia Respiratoria|Tension Arterial|
      |23-1111111-9|Le agarro dengue|Emergencia         |38         |70                 |15                      |120/80          |
    Then el sistema muestra el siguiente error: "Paciente no encontrado"

  Scenario Outline: Intento de ingreso con datos mandatorios omitidos
    Given que estan registrados los siguientes pacientes:
      |Cuil        |Apellido|Nombre | Obra Social      |
      |23-1234567-9|Nunez   |Marcelo| Subsidio de Salud|
      |27-4567890-3|Dufour  | Alexandra| Swiss Medical |
      |23-4567899-2|Estrella|Patricio  | Fondo de Bikini SA|

    When Ingresan a urgencias los siguientes pacientes:
      | Cuil        | Informe        | Nivel de Emergencia | Temperatura | Frecuencia Cardiaca | Frecuencia Respiratoria | Tension Arterial |
      | 23-1234567-9 | <informe>      | <nivel_emergencia>  | <temperatura> | <frec_cardiaca>     | <frec_respiratoria>     | <tension>        |

    Then el sistema muestra el siguiente error: "<mensaje_error>"

    Examples:
      | informe        | nivel_emergencia | temperatura | frec_cardiaca | frec_respiratoria | tension | mensaje_error                              |
      | Dolor intenso   |                  | 38.5        | 85            | 18                | 130/85  | El nivel de emergencia es obligatorio    |
      | Fiebre alta     | Emergencia       |             | 90            | 20                | 140/90  | La temperatura es obligatoria           |
      | Dificultad respiratoria | Emergencia | 37.8      |               | 25                | 120/80 |La frecuencia cardíaca es obligatoria    |
      | Convulsiones    | Urgencia         | 39.2        | 110           |                   | 150/95  | La frecuencia respiratoria es obligatoria|
      | Trauma grave    | Emergencia       | 36.5        | 75            | 16                |         | La tensión arterial es obligatoria       |
      |                 | Emergencia       | 38.0        | 80            | 17                | 125/82  | El informe médico es obligatorio         |