export enum DecisionStatus {
  U_PROCESU,
  PRIHVACENA = "prihvacena",
  ODBIJENA = "odbijena",
  PRIHVACENA_FAZA_1 = "prihvacena na osnovu trenutka nastanka",
  PRIHVACENA_FAZA_2 = "prihvacena na osnovu neuroloskog pregleda",
  PRIHVACENA_FAZA_3 = "prihvacena na osnovu neuroloskog pregleda i anamneze",
  PRIHVACENA_FAZA_4 = "prihvacena na osnovu NIHHS skora",
  PRIHVACENA_FAZA_5 = "prihvacena na osnovu CT-a glave",
  PRIHVACENA_FAZA_6 = "prihvacena na osnovu NIHHS skora, CT-a glave i laboratorije krvi"
}
