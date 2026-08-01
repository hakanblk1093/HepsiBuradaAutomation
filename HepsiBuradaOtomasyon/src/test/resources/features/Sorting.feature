@siralama
Feature: Hepsiburada Arama Sonuçlarını Sıralama

  Scenario: Sonuçların en düşük fiyata göre sıralanması
    Given kullanıcı Hepsiburada ana sayfasındadır
    When kullanıcı arama kutusuna "iphone" yazıp arama yapar
    And kullanıcı ilk ürünün fiyatını kaydeder
    And kullanıcı sonuçları "En düşük fiyat" seçeneğine göre sıralar
    Then seçili sıralama "En düşük fiyat" olmalıdır
    And ilk ürünün fiyatı kaydedilen fiyattan düşük olmalıdır

  Scenario: Sonuçların en yüksek fiyata göre sıralanması
    Given kullanıcı Hepsiburada ana sayfasındadır
    When kullanıcı arama kutusuna "iphone" yazıp arama yapar
    And kullanıcı ilk ürünün fiyatını kaydeder
    And kullanıcı sonuçları "En yüksek fiyat" seçeneğine göre sıralar
    Then seçili sıralama "En yüksek fiyat" olmalıdır
    And ilk ürünün fiyatı kaydedilen fiyattan yüksek olmalıdır
