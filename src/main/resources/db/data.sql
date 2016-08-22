insert into users(username,password,first_name,last_name,birth_date,gender,enabled,role) 
values('jane.doe','jane','Jane','Doe','1980-10-10','F',true,'ROLE_USER');

insert into users(username,password,first_name,last_name,birth_date,gender,enabled,role) 
values('john.doe','john','John','Doe','1980-07-30','M',true,'ROLE_USER');


insert into profile_pictures(id,user_id,image) values (seq_pictures.nextval, 1, file_read('src/main/resources/db/img/user-user.jpeg'));
insert into profile_pictures(id,user_id,image) values (seq_pictures.nextval, 1, file_read('src/main/resources/db/img/user-user-2.jpeg'));
insert into profile_pictures(id,user_id,image) values (seq_pictures.nextval, 2, file_read('src/main/resources/db/img/user-admin.jpeg'));

insert into posts(id,user_id,text) values (1, 1, 'So, here I am :) Hello to all of you!');
insert into posts(id,user_id,text) values (2, 1, 'Hi guys, check out this movie: http://www.imdb.com/title/tt3195644/?ref_=hm_rec_tt really scarry');
insert into posts(id,user_id,text) values (3, 2, 'Anyone interested in hanging up?');

insert into likes(user_id,post_id) values(2,1);
insert into likes(user_id,post_id) values(1,3);

insert into obj_ids(obj_type,obj_next_id) values('Post',100);
insert into obj_ids(obj_type,obj_next_id) values('ProfilePicture',0);
