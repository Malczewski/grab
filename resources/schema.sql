create table articles (
	id integer constraint articles_pk primary key,
	url varchar(300) not null unique,
	title varchar(300),
	category varchar(100),
	category_ru varchar(100),
	content text,
	doc_date date)       ;
create index on articles(doc_date);