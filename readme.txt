1. В задании не написано должен ли сервис работать с разными валютами, и конвертировать их. Поэтому я интерпретировал задание так, что
сервис должен просто оперировать некими "условными" деньгами.
2. В API специально не соблюдается REST-правило - "действие над данными = http-метод".
На мой взгляд в данном сервисе такое API бы не подошло. Поэтому все три метода - POST, а само действие над данными - часть URL.
