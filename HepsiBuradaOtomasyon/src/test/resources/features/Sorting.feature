@siralama
Feature: Hepsiburada Arama Sonuçlarını Sıralama

  Scenario: Sonuçların en düşük fiyata göre sıralanması
    Given kullanıcı Hepsiburada ana sayfasındadır
    When kullanıcı arama kutusuna "iphone" yazıp arama yapar
    And kullanıcı sonuçları "En düşük fiyat" seçeneğine göre sıralar
    Then seçili sıralama "En düşük fiyat" olmalıdır
    And ürünler artan fiyata göre sıralanmış olmalıdır

  Scenario: Sonuçların en yüksek fiyata göre sıralanması
    Given kullanıcı Hepsiburada ana sayfasındadır
    When kullanıcı arama kutusuna "iphone" yazıp arama yapar
    And kullanıcı sonuçları "En yüksek fiyat" seçeneğine göre sıralar
    Then seçili sıralama "En yüksek fiyat" olmalıdır
    And ürünler azalan fiyata göre sıralanmış olmalıdır

  Scenario: Sonuçların çok satanlara göre sıralanması
    Given kullanıcı Hepsiburada ana sayfasındadır
    When kullanıcı arama kutusuna "iphone" yazıp arama yapar
    And kullanıcı sonuçları "Çok satanlar" seçeneğine göre sıralar
    Then seçili sıralama "Çok satanlar" olmalıdır

  Scenario: Sonuçların yüksek puanlılara göre sıralanması
    Given kullanıcı Hepsiburada ana sayfasındadır
    When kullanıcı arama kutusuna "iphone" yazıp arama yapar
    And kullanıcı sonuçları "Yüksek puanlılar" seçeneğine göre sıralar
    Then seçili sıralama "Yüksek puanlılar" olmalıdır

  Scenario: Sonuçların indirim oranına göre sıralanması
    Given kullanıcı Hepsiburada ana sayfasındadır
    When kullanıcı arama kutusuna "iphone" yazıp arama yapar
    And kullanıcı sonuçları "İndirim oranı" seçeneğine göre sıralar
    Then seçili sıralama "İndirim oranı" olmalıdır
