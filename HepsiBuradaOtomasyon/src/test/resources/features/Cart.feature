Feature: Hepsiburada Sepete Ekleme İşlemleri

  Scenario: Ürünün sepete eklenmesi ve onay mesajının görüntülenmesi

    Given kullanıcı Hepsiburada ana sayfasındadır
    When kullanıcı arama kutusuna "iphone" yazıp arama yapar
    And kullanıcı arama sonucundaki ilk ürüne tıklar
    And kullanıcı ürün detay sayfasında sepete ekle butonuna tıklar
    Then sepete ekleme onay mesajı görüntülenmelidir
    And sepet simgesindeki ürün sayısı "1" olmalıdır

  Scenario: Sepete eklenen ürünün sepetten çıkarılması

    Given kullanıcı Hepsiburada ana sayfasındadır
    When kullanıcı arama kutusuna "iphone" yazıp arama yapar
    And kullanıcı arama sonucundaki ilk ürüne tıklar
    And kullanıcı ürün detay sayfasında sepete ekle butonuna tıklar
    And kullanıcı sepete git bağlantısına tıklar
    And kullanıcı sepetten ürünü çıkarır
    Then sepetin boş olduğu mesajı görüntülenmelidir

  Scenario: Sepetteki ürün adedinin arttırılması

    Given kullanıcı Hepsiburada ana sayfasındadır
    When kullanıcı arama kutusuna "iphone" yazıp arama yapar
    And kullanıcı arama sonucundaki ilk ürüne tıklar
    And kullanıcı ürün detay sayfasında sepete ekle butonuna tıklar
    And kullanıcı sepete git bağlantısına tıklar
    And kullanıcı sepette ürün adedini arttırır
    Then sepetteki ürün adedi "2" olmalıdır

  Scenario: Sepetteki ürün adedinin azaltılması

    Given kullanıcı Hepsiburada ana sayfasındadır
    When kullanıcı arama kutusuna "iphone" yazıp arama yapar
    And kullanıcı arama sonucundaki ilk ürüne tıklar
    And kullanıcı ürün detay sayfasında sepete ekle butonuna tıklar
    And kullanıcı sepete git bağlantısına tıklar
    And kullanıcı sepette ürün adedini arttırır
    And kullanıcı sepette ürün adedini azaltır
    Then sepetteki ürün adedi "1" olmalıdır

  Scenario: Sepete birden fazla farklı ürün eklenmesi

    Given kullanıcı Hepsiburada ana sayfasındadır
    When kullanıcı arama kutusuna "iphone" yazıp arama yapar
    And kullanıcı arama sonucundaki ilk ürüne tıklar
    And kullanıcı ürün detay sayfasında sepete ekle butonuna tıklar
    And kullanıcı arama kutusuna "kitap" yazıp arama yapar
    And kullanıcı arama sonucundaki ilk ürüne tıklar
    And kullanıcı ürün detay sayfasında sepete ekle butonuna tıklar
    Then sepet simgesindeki ürün sayısı "2" olmalıdır