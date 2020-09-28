create table "controller_info"."request_log"
(
	id bigserial not null
		constraint requestlog_pk
			primary key,
	class_parent text not null,
	method_signature text not null,
	time_request timestamp,
	uri text,
	http_method text,
	return_code text,
	exception text
);

comment on table "controller_info"."request_log" is '���������� � ��������';

comment on column "controller_info"."request_log".class_parent is '����� ������';

comment on column "controller_info"."request_log".method_signature is '��������� ������ � ����� ����������';

comment on column "controller_info"."request_log".time_request is '����� �������';

comment on column "controller_info"."request_log".return_code is '������� ������';



