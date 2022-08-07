# LogoBitirmeProjesi-Ticket
## Proje Konusu:
Online uçak ve otobüs bilet satış uygulaması için gerekli API’lerın
yazılması. Ayrıca sisteme yolculuk seferlerinin sisteme girilmesi için de gerekli
API’ler yazılmalıdır.
Gereksinimler
* Kullanıcılar sisteme kayıt ve login olabilmelidir.
* Kullanıcı kayıt işleminden sonra mail gönderilmelidir.
* Kullanıcı şifresi istediğiniz bir hashing algoritmasıyla database
kaydedilmelidir.
* Admin kullanıcı yeni sefer ekleyebilir, iptal edebilir, toplam bilet satışını,
bu satıştan elde edilen toplam ücreti görebilir.
* Kullanıcılar şehir bilgisi, taşıt türü(uçak & otobüs) veya tarih bilgisi ile
tüm seferleri arayabilmelidir.
* Bireysel kullanıcı aynı sefer için en fazla 5 bilet alabilir.
* Bireysel kullanıcı tek bir siparişte en fazla 2 erkek yolcu için bilet
alabilir.
* Kurumsal kullanıcı aynı sefer için en fazla 20 bilet alabilir.
* Satın alma işlemi başarılı ise işlem tamamlanmalı ve asenkron olarak
bilet detayları kullanıcının telefona numarasına mesaj gönderilmeli.
* Mesaj ve mail gönderme işlemleri için sadece Database kayıt etme
işlemi yapması yeterlidir. Fakat bu işlemler tek bir Servis(uygulama)
üzerinden ve polimorfik davranış ile yapılmalıdır.
* Kullancılar aldığı biletleri görebilmelidir.
## Sistem Kabulleri
* Kullanıcılar bireysel ve kurumsal olabilir.
* Uçak yolcu kapasitesi: 189
* Otobüs yolcu kapasitesi: 45
* Ödeme şekli sadece Kredi kartı ve Havale / EFT olabilir.
* Mesaj ve Mail gönderim işlemleri Asenkron olmalıdır.
* Ödeme Servisi işlemleri Senkron olmalıdır.
## Kullanılacak Teknolojiler;
* Min Java 8
* JUnit
* RESTful
* Spring Boot
* MySQL / Postgre / MongoDB (Her servis farklı db kullanabilir ihtiyaca
göre kullanabilirsiniz)
* RabbbitMQ
