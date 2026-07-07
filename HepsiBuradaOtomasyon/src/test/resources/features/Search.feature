Feature: Hepsiburada Ürün Arama İşlemleri

  Scenario: Geçerli bir ürün adıyla arama yapıldığında sonuç listesinin görüntülenmesi
    Given kullanıcı Hepsiburada ana sayfasındadır
    When kullanıcı arama kutusuna "iphone" yazıp arama yapar
    Then arama sonuçları listelenmelidir