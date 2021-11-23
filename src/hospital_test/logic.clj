(ns hospital_test.logic)

(defn cabe-na-fila?
  [hospital, departamento]
  (-> hospital
      departamento
      count
      (< 5)))