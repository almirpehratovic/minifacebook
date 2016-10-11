
create table users(
	id int not null auto_increment,
	username varchar(40) not null,
	password varchar(40) not null,
	enabled boolean,
	first_name varchar(40) not null,
	last_name varchar(80) not null,
	birth_date date,
	gender varchar(1) not null,
	role varchar(40) not null,
	country varchar2(40),
	primary key (id)
);

create table posts (
	id int not null,
	user_id int not null,
	datetime timestamp not null default current_timestamp,
	text varchar(4000) not null,
	primary key (id),
	constraint fk_posts_1 foreign key (user_id) references users(id)
);

create table likes (
	id int not null auto_increment,
	user_id int not null,
	post_id int not null,
	primary key (id),
	constraint fk_likes_1 foreign key (user_id) references users(id),
	constraint fk_likes_2 foreign key (post_id) references posts(id)
);


create table profile_pictures(
	id int not null auto_increment,
	user_id int not null,
	image blob not null,
	primary key (id),
	constraint fk_profile_pictures_1 foreign key (user_id) references users(id)
);

create table statuses(
	id int not null auto_increment,
	user_id int not null,
	text varchar(4000),
	primary key (id),
	constraint fk_status_1 foreign key (user_id) references users(id)
);

create table obj_ids (
    obj_type varchar(40) not null,
    obj_next_id int not null,
    primary key (obj_type)
);

create sequence seq_pictures start with 5;