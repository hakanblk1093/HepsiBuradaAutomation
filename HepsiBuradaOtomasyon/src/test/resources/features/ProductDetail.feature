Feature: Hepsiburada Ürün Detay Sayfası

  Scenario: Arama sonucundan ürüne tıklayınca detay sayfasının açılması
    Given kullanıcı Hepsiburada ana sayfasındadır
    When kullanıcı arama kutusuna "iphone" yazıp arama yapar
    And kullanıcı arama sonucundaki ilk ürüne tıklar
    Then ürün detay sayfası açılmalı ve ürün adı görüntülenmelidir

  Scenario: Ürün detay sayfasında fiyatın görüntülenmesi
    Given kullanıcı Hepsiburada ana sayfasındadır
    When kullanıcı arama kutusuna "iphone" yazıp arama yapar
    And kullanıcı arama sonucundaki ilk ürüne tıklar
    Then ürün fiyatı görüntülenmelidir
