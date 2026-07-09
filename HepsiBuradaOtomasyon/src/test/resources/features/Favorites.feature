Feature: Hepsiburada Favorilere Ekleme

  Scenario: Giriş yapmamış kullanıcı favorilere ekleme denediğinde giriş sayfasına yönlendirilir
    Given kullanıcı Hepsiburada ana sayfasındadır
    When kullanıcı arama kutusuna "iphone" yazıp arama yapar
    And kullanıcı arama sonucundaki ilk ürüne tıklar
    And kullanıcı ürün detay sayfasında favorilere ekle butonuna tıklar
    Then kullanıcı giriş sayfasına yönlendirilmelidir
