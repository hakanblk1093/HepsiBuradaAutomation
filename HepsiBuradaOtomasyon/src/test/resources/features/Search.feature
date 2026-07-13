Feature: Hepsiburada Ürün Arama İşlemleri

  Scenario: Geçerli bir ürün adıyla arama yapıldığında sonuç listesinin görüntülenmesi
    Given kullanıcı Hepsiburada ana sayfasındadır
    When kullanıcı arama kutusuna "iphone" yazıp arama yapar
    Then arama sonuçları listelenmelidir

  Scenario: Anlamsız bir kelimeyle arama yapıldığında sonuç bulunamadı mesajının görüntülenmesi
    Given kullanıcı Hepsiburada ana sayfasındadır
    When kullanıcı arama kutusuna "asdkjfhqwlekjhasdf123456789xyz" yazıp arama yapar
    Then sonuç bulunamadı mesajı görüntülenmelidir