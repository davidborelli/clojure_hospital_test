(ns hospital_test.logic-test
  (:require [clojure.test :refer :all]
            [hospital_test.logic :refer :all]))

(deftest cabe-na-fila?-test

  ; boundary tests
  ; exatamente na borda e one off. -1, +1. <=, >=, =.

  ; Muito importante um check list e ter mapeado testes mais interessantes, como esse de borda

  ; Borda do zero
  (testing "Que cabe na fila"
    (is (cabe-na-fila? {:espera []}, :espera)))

  ; Borda do limite
  (testing "Que não cabe na fila quando a fila está cheia"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5]}, :espera))))

  ; one off da borda do limite para cima
  (testing "Que não cabe na fila quando a fila está cheia"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5 6]}, :espera))))

  (testing "Que cabe na fila quando tem pouco menos que uma fila cheia"
    (is (cabe-na-fila? {:espera [1 2 3 4]}, :espera)))

  (testing "Que cabe na fila quando tem pouca gente na fila"
    (is (cabe-na-fila? {:espera [1 2]}, :espera))))