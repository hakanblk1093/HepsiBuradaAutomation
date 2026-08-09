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

  Scenario: Geçersiz email formatı ile üye olunamaması
    Given kullanıcı Hepsiburada ana sayfasındadır
    When kullanıcı üye ol bağlantısına tıklar
    And kullanıcı üye ol sayfasında geçersiz formatta "asdadasdasd" ile devam eder
    Then geçerli email formatı uyarısı görüntülenmelidir
