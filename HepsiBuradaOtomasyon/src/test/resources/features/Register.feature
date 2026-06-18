Feature: Hepsiburada Üyelik İşlemleri

  Scenario: Üye Ol sayfasına yönlendirme
    Given kullanıcı Hepsiburada ana sayfasındadır
    When kullanıcı üye ol bağlantısına tıklar
    Then üye ol sayfası açılmalıdır

  Scenario: Boş email ile üye olunamaması
    Given kullanıcı Hepsiburada ana sayfasındadır
    When kullanıcı üye ol bağlantısına tıklar
    And kullanıcı email girmeden devam eder
    Then email zorunluluk uyarısı görüntülenmelidir