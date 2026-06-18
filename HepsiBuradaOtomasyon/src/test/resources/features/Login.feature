Feature: Hepsiburada Giriş İşlemleri

  Scenario: Geçersiz bilgilerle giriş yapılamaması
    Given kullanıcı Hepsiburada ana sayfasındadır
    And kullanıcı giriş yap bağlantısına tıklar
    When kullanıcı geçersiz "test@test.com" ve "yanlissifre" ile giriş yapar
    Then bir hata mesajı görüntülenmelidir

  Scenario: Geçersiz email formatı ile giriş yapılamaması
    Given kullanıcı Hepsiburada ana sayfasındadır
    And kullanıcı giriş yap bağlantısına tıklar
    When kullanıcı geçersiz formatta "asdadasdasd" ile giriş yapar
    Then geçerli email uyarısı görüntülenmelidir
    
  Scenario: Şifremi unuttum sayfasına yönlendirme
    Given kullanıcı Hepsiburada ana sayfasındadır
    And kullanıcı giriş yap bağlantısına tıklar
    When kullanıcı şifremi unuttum bağlantısına tıklar
    Then şifre yenileme sayfası açılmalıdır