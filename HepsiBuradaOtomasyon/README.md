# Hepsiburada BDD Test Otomasyon Projesi

Hepsiburada web sitesi üzerinde Behavior Driven Development (BDD) yaklaşımıyla geliştirilmiş bir test otomasyon projesidir. Selenium WebDriver ve Cucumber kullanılarak kullanıcı giriş, üyelik ve ürün arama senaryoları otomatize edilmiştir.

## Kullanılan Teknolojiler

- Java 17
- Selenium WebDriver 4.44.0
- Cucumber 7.34.3
- JUnit Platform 5.13.4 (BOM)
- Maven

## Proje Mimarisi

Proje, Page Object Model (POM) tasarım deseni ile yapılandırılmıştır.

- **pages/** — Page Object sınıfları (BasePage, HomePage, LoginPage, RegisterPage, SearchPage)
- **stepdefinitions/** — Cucumber step tanımları ve Hooks (ekran görüntüsü dahil)
- **runners/** — CucumberRunner test çalıştırıcı
- **utils/** — DriverManager ve ConfigReader yardımcı sınıfları
- **resources/features/** — Gherkin senaryoları (Login.feature, Register.feature, Search.feature)

## Test Senaryoları

### Giriş (Login)

1. **Geçersiz bilgilerle giriş** — Yanlış email ve şifre girildiğinde hata mesajı doğrulanır.
2. **Geçersiz email formatı** — Hatalı formatta email girildiğinde validasyon uyarısı doğrulanır.
3. **Şifremi unuttum yönlendirmesi** — Bağlantının şifre yenileme sayfasına yönlendirdiği doğrulanır.

### Üyelik (Register)

1. **Üye Ol sayfasına yönlendirme** — Üye ol bağlantısına tıklandığında üye ol sayfasının açıldığı doğrulanır.
2. **Boş email ile üye olunamaması** — Email girilmeden devam edildiğinde zorunluluk uyarısının görüntülendiği doğrulanır.

### Arama (Search)

1. **Geçerli bir ürün adıyla arama** — Arama kutusuna "iphone" yazılıp arama yapıldığında sonuç listesinin görüntülendiği doğrulanır.
2. **Sonuç bulunamayan arama** — Anlamsız bir metinle arama yapıldığında hiç ürün listelenmediği doğrulanır.

## Öne Çıkan Özellikler

- Page Object Model ile sürdürülebilir kod yapısı
- BasePage üzerinden ortak metotların merkezi yönetimi
- Explicit Wait kullanımı (Thread.sleep yerine WebDriverWait)
- Çerez/consent engelinin JavaScript click ile aşılması
- Test başarısız olduğunda otomatik ekran görüntüsü (rapora gömülür ve target/screenshots altına kaydedilir)
- Kimlik bilgilerinin config.properties ile koddan ayrılması

## Kurulum ve Çalıştırma

git clone https://github.com/hakanblk1093/HepsiBuradaAutomation.git

mvn clean install

mvn test

## Test Raporu

Testler çalıştıktan sonra HTML raporu target/cucumber-report.html konumunda oluşur.

## Notlar

- Başarılı giriş senaryosu, Hepsiburada'nın bot koruması nedeniyle kapsam dışında bırakılmıştır.
- config.properties dosyası güvenlik nedeniyle repoya dahil edilmemiştir.