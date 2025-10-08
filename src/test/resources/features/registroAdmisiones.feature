Feature: Registro de admisiones en el módulo de urgencias
  Como enfermera
  Quiero poder registrar las admisiones de los pacientes a urgencias
  Para determinar qué pacientes tienen mayor prioridad de atención

  #---------------------------------------
  Rule: El sistema debe registrar los datos obligatorios del ingreso
  #---------------------------------------

    Scenario Outline: Registro exitoso cuando el paciente existe y los datos son correctos
      Given que el paciente <paciente> existe en el sistema
      And la enfermera ingresa todos los datos obligatorios
      And el nivel de emergencia es <nivel>
      When se registra el ingreso del paciente
      Then el sistema guarda el ingreso con estado "PENDIENTE"
      And el paciente entra en la lista de espera

      Examples:
        | paciente | nivel          |
        | Juan     | Crítica        |
        | María    | Urgencia       |
        | Pedro    | Sin Urgencia   |

    Scenario: El paciente no existe en el sistema
      Given que el paciente "Carlos" no existe en el sistema
      When la enfermera intenta registrar el ingreso
      Then el sistema solicita crear primero el paciente
      And luego permite registrar el ingreso con estado "PENDIENTE"

    Scenario Outline: Faltan datos obligatorios durante el ingreso
      Given que el paciente <paciente> existe en el sistema
      And falta el campo <campo_faltante>
      When la enfermera intenta registrar el ingreso
      Then el sistema muestra un mensaje de error indicando el dato faltante

      Examples:
        | paciente | campo_faltante           |
        | Laura    | frecuencia cardíaca      |
        | Martín   | frecuencia respiratoria  |
        | Ana      | nivel de emergencia      |

  #---------------------------------------
  Rule: Validaciones sobre los valores fisiológicos
  #---------------------------------------

    Scenario Outline: Valores negativos no son aceptados
      Given que la enfermera intenta registrar valores fisiológicos
      And la frecuencia cardíaca es <frecuencia_cardiaca> lpm
      And la frecuencia respiratoria es <frecuencia_respiratoria> rpm
      When se intenta registrar el ingreso
      Then el sistema muestra un mensaje de error indicando que los valores no pueden ser negativos

      Examples:
        | frecuencia_cardiaca | frecuencia_respiratoria |
        | -80                 | 16                      |
        | 90                  | -12                     |
        | -70                 | -8                      |

  #---------------------------------------
  Rule: Priorización por nivel de emergencia
  #---------------------------------------

    Scenario Outline: Paciente con mayor nivel de emergencia tiene prioridad
      Given que el paciente A tiene nivel <nivel_A>
      And el paciente B tiene nivel <nivel_B>
      When ambos están en espera
      Then debe ser atendido primero el paciente con el nivel de mayor prioridad

      Examples:
        | nivel_A     | nivel_B      | prioridad esperada     |
        | Crítica      | Urgencia     | Paciente A             |
        | Emergencia   | Urgencia Menor | Paciente A          |
        | Urgencia     | Sin Urgencia | Paciente A             |

    Scenario Outline: Mismo nivel de emergencia, orden por hora de ingreso
      Given que el paciente A y B tienen el mismo nivel <nivel>
      And el paciente A ingresó a las <hora_A>
      And el paciente B ingresó a las <hora_B>
      When se ordena la cola de atención
      Then el paciente que ingresó primero tiene prioridad

      Examples:
        | nivel        | hora_A  | hora_B  | prioridad esperada |
        | Emergencia   | 10:00   | 10:15   | Paciente A         |
        | Urgencia     | 08:30   | 08:45   | Paciente A         |
        | Crítica      | 09:10   | 09:12   | Paciente A         |
