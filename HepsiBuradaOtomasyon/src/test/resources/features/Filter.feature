@filtre
Feature: Hepsiburada Arama Sonuçlarını Filtreleme

  Scenario: Marka filtresi uygulandığında sadece o markanın ürünlerinin listelenmesi
    Given kullanıcı Hepsiburada ana sayfasındadır
    When kullanıcı arama kutusuna "iphone" yazıp arama yapar
    And kullanıcı "Apple" markasını filtreler
    Then uygulanan filtreler arasında "Apple" görüntülenmelidir
    And listelenen tüm ürünler "Apple" markasına ait olmalıdır

  Scenario: Uygulanan marka filtresinin kaldırılması
    Given kullanıcı Hepsiburada ana sayfasındadır
    When kullanıcı arama kutusuna "iphone" yazıp arama yapar
    And kullanıcı "Apple" markasını filtreler
    And kullanıcı "Apple" marka filtresini kaldırır
    Then uygulanan filtre kalmamalıdır
