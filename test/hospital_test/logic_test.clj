(ns hospital_test.logic-test
  (:use clojure.pprint)
  (:require [clojure.test :refer :all]
            [hospital_test.logic :refer :all]
            [hospital_test.model :as ht.model]))

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

  ; Dentro das bordas
  (testing "Que cabe na fila quando tem gente mas não está cheia"
    (is (cabe-na-fila? {:espera [1 2 3 4]}, :espera))
    (is (cabe-na-fila? {:espera [1 2]}, :espera)))

  (testing "Que não cabe quando departamento não existe"
    (is (not (cabe-na-fila? {:espera [1 2 3 4]}, :raio-x)))))



(deftest chega-em-test
  (let [hospital-cheio {:espera [1 21 32 23 43]}]

    (testing "aceita pessoas enquanto cabem pessoas na fila"
      (is (= {:espera [1 2 3 4 5]}
             (chega-em {:espera [1 2 3 4]}, :espera, 5)))

      (is (= {:espera [1 2 5]}
             (chega-em {:espera [1 2]}, :espera, 5)))

      ;(is (= {:hospital {:espera [1 2 3 4 5]}, :resultado :sucesso}
      ;       (chega-em {:espera [1 2 3 4]}, :espera, 5)))
      ;
      ;(is (= {:hospital {:espera [1 2 5]}, :resultado :sucesso}
      ;       (chega-em {:espera [1 2]}, :espera, 5)))

      )

    (testing "não aceita quando não cabe na fila"
      ; Verificando que uma exception foi jogada
      ; código clássico ruim, usamos uma exception GENERICA.
      ; que qualquer outro erro genérico irá lançar a mesma exception
      ; podendo induzir ao erro, quando vamos acreditar que deu certo, mas deu errado
      (is (thrown? clojure.lang.ExceptionInfo (chega-em hospital-cheio, :espera 76)))

      ;(is (thrown? IllegalStateException
      ;             (chega-em {:espera [1 35 42 64 21]}, :espera 76)))

      ; outra abordagem, do nil
      ; mas o perigo do swap, teriamos que trabalhar em outro ponto a condição de erro
      ;(is (nil? (chega-em {:espera [1 21 32 23 43]}, :espera 98)))

      ; outra maneira de testar, onde ao invés de como Java, utilizar o tipo da
      ; exception para entender o TIPO de erro que ocorreu, estamos usando os dados
      ; da exception para isso. Menos sensível que a mensagem de erro
      ; mas ainda eh uma validação trabalhosa
      ;(is (try
      ;      (chega-em {:espera [12 13 14 19 43]}, :espera 44)
      ;      false
      ;      (catch clojure.lang.ExceptionInfo e
      ;        (= :impossivel-colocar-pessoa-na-fila (:tipo (ex-data e)))
      ;        )))

      ;(is (= {:hospital hospital-cheio, :resultado :impossivel-colocar-pessoa-na-fila}
      ;       (chega-em hospital-cheio, :espera 98)))
      )))

(deftest transfere-test
  (testing "transfere pessoa se cabe"
    (let [hospital-original {:espera [5], :raio-x []}]
      (is (= {:espera [], :raio-x [5]}
             (transfere hospital-original :espera :raio-x))))

    (let [hospital-original {:espera (conj ht.model/fila-vazia 51 5), :raio-x (conj ht.model/fila-vazia 13)}]
      (is (= {:espera [5], :raio-x [13 51]}
             (transfere hospital-original :espera :raio-x))))
    )

  (testing "recusa pessoa se não cabe"
    (let [hospital-cheio {:espera [5], :raio-x [1 2 53 42 13]}]
      (is (thrown? clojure.lang.ExceptionInfo
                   (transfere hospital-cheio :espera :raio-x))))))
