Feature: Modulo de Urgencias
  Esta Feature esta relacionada al registro de ingresos de pacientes en la sala de urgencias respetando su nivel de prioridad y el horario de llegada.

  Background:
    Given que la siguiente enfermera esta registrada:
      | Nombre | Apellido |
      | Susana | Gimenez  |

    Scenario: Ingreso del primer paciente a la lista de espera de urgencias
      Given que estan registrados los siguientes pacientes:
        | Cuil         | Apellido | Nombre    | Obra Social       |
        | 23-1234567-9 | Nunes    | Marcelo   | Subsidio de Salud |
        | 27-4567890-3 | Dufour   | Alexandra |Swiss Medical      |
      When Ingresa a urgencias el siguiente paciente:
        | Cuil         | Informe          | Nivel de Emergencia | Temperatura | Fecuencia Cardiaca | Frecuencia Respiratoria | Tension Arterial |
        | 23-1234567-9 | Le agarro dengue | Emergencia          | 38          | 70                 | 15                      |128/80            |

      Then La lista de espera esta ordenada por el cuil de la siguiente manera:
        | 23-1234567-9 |

    Scenario: Ingreso de un paciente de bajo nivel de emergencia y luego otro de alto nivel de emergencia
      Given Que estan registrados los siguientes pacientes:
        | Cuil         | Apellido | Nombre    | Obra Social        |
        | 23-1234567-9 | Nunes    | Marcelo   | Subsidio de Salud  |
        | 27-4567890-3 | Dufour   | Alexandra | Swiss Medical      |
        | 23-4567899-2 | Estrella | Patricio  | Fondo de Bikini SA |
      When Ingresa a urgencias el siguiente paciente:
        | Cuil         | Informe          | Nivel de Emergencia | Temperatura | Fecuencia Cardiaca | Frecuencia Respiratoria | Tension Arterial |
        | 23-1234567-9 | Le agarro dengue | Emergencia          | 38          | 70                 | 15                      | 128/80           |
        | 23-4567899-2 | Le duele el ojo  | Sin Urgencia        | 37          | 70                 | 15                      | 100/80           |

      Then La lista de espera esta ordenada por el cuil de la siguiente manera:
        | 23-1234567-9 |
        | 23-4567899-2 |