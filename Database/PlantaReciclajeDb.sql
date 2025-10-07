create database PlantaReciclajeDB;
use PlantaReciclajeDB;

create table Materiales(
    idMaterial int primary key auto_increment,
    tipoMaterial enum('Plastico', 'Vidrio', 'Metal', 'Papel', 'Carton') not null,
    precioPorKg decimal(10,2) not null,
    constraint PK_Materiales primary key (idMaterial)
);

create table Administradores(
    idAdministrador int primary key auto_increment,
    nombre varchar(100) not null,
    correoElectronico varchar(100) not null unique,
    contrasena varchar(100) not null,
    constraint PK_Administradores primary key (idAdministrador)
);

create table Entregas(
    idEntrega int primary key auto_increment,
    idMaterial int not null,
    cantidadKg decimal(10,2) not null,
    fechaEntrega date not null,
    nombreProveedor varchar(100) not null,
    compensacion decimal(10,2) not null,
    constraint PK_Entregas primary key (idEntrega),
    constraint FK_Entregas_Materiales foreign key (idMaterial) references Materiales(idMaterial)
);

create table CookieAuth (
    id int auto_increment primary key,
    selector varchar(255) unique not null,
    token_hash varchar(255) not null,
    idAdministrador int not null,
    fechaExpiracion datetime not null,
    constraint FK_CookieAuth_Admin
        foreign key (idAdministrador) references Administradores(idAdministrador)
        on delete cascade
);
