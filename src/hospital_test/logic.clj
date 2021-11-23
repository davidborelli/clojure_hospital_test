(ns hospital_test.logic)

; existe problema de condicional quando o departamento não existe
;(defn cabe-na-fila?
;  [hospital, departamento]
;  (-> hospital
;          departamento
;          count
;          (< 5)))

; funciona para o caso de não ter o departamento
;(defn cabe-na-fila?
;  [hospital, departamento]
;  (when-let [fila (get hospital departamento)]
;    (-> fila
;        count
;        (< 5))))

; funciona utilizando o some->
; desvantagem/vantagem "menos explicito"
; desvantagem qq um que der nil, devolve nil
(defn cabe-na-fila?
  [hospital, departamento]
  (some-> hospital
          departamento
          count
          (< 5)))