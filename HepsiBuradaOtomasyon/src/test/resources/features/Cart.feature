Feature: Hepsiburada Sepete Ekleme İşlemleri

  Scenario: Ürünün sepete eklenmesi ve onay mesajının görüntülenmesi
    Given kullanıcı Hepsiburada ana sayfasındadır
    When kullanıcı arama kutusuna "iphone" yazıp arama yapar
    And kullanıcı arama sonucundaki ilk ürüne tıklar
    And kullanıcı ürün detay sayfasında sepete ekle butonuna tıklar
    Then sepete ekleme onay mesajı görüntülenmelidir
    And sepet simgesindeki ürün sayısı "1" olmalıdır