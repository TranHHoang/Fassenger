USE [master]
GO
/****** Object:  Database [Fassenger]    Script Date: 24-Mar-19 5:16:20 PM ******/
CREATE DATABASE [Fassenger]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Fassenger', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.MSSQLSERVER\MSSQL\DATA\Fassenger.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB ), 
 FILEGROUP [images] CONTAINS FILESTREAM  DEFAULT
( NAME = N'images', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.MSSQLSERVER\MSSQL\DATA\Fassenger' , MAXSIZE = UNLIMITED)
 LOG ON 
( NAME = N'Fassenger_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.MSSQLSERVER\MSSQL\DATA\Fassenger_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
ALTER DATABASE [Fassenger] SET COMPATIBILITY_LEVEL = 140
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Fassenger].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Fassenger] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Fassenger] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Fassenger] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Fassenger] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Fassenger] SET ARITHABORT OFF 
GO
ALTER DATABASE [Fassenger] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [Fassenger] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Fassenger] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Fassenger] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Fassenger] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Fassenger] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Fassenger] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Fassenger] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Fassenger] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Fassenger] SET  ENABLE_BROKER 
GO
ALTER DATABASE [Fassenger] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Fassenger] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Fassenger] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Fassenger] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Fassenger] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Fassenger] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Fassenger] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Fassenger] SET RECOVERY FULL 
GO
ALTER DATABASE [Fassenger] SET  MULTI_USER 
GO
ALTER DATABASE [Fassenger] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Fassenger] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Fassenger] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Fassenger] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Fassenger] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'Fassenger', N'ON'
GO
ALTER DATABASE [Fassenger] SET QUERY_STORE = OFF
GO
USE [Fassenger]
GO
/****** Object:  Table [dbo].[FassengerMessage]    Script Date: 24-Mar-19 5:16:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[FassengerMessage](
	[Id] [uniqueidentifier] ROWGUIDCOL  NOT NULL,
	[UserName] [varchar](100) NULL,
	[DateCreated] [datetime] NULL,
	[ImageContent] [varbinary](max) FILESTREAM  NULL,
	[TextContent] [nvarchar](max) NULL,
UNIQUE NONCLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY] FILESTREAM_ON [images]
GO
/****** Object:  Table [dbo].[FassengerUser]    Script Date: 24-Mar-19 5:16:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[FassengerUser](
	[Id] [uniqueidentifier] ROWGUIDCOL  NOT NULL,
	[UserName] [varchar](100) NOT NULL,
	[Nickname] [nvarchar](100) NULL,
	[UserPassword] [varchar](100) NOT NULL,
	[Avatar] [varbinary](max) FILESTREAM  NULL,
PRIMARY KEY CLUSTERED 
(
	[UserName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY] FILESTREAM_ON [images],
UNIQUE NONCLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] FILESTREAM_ON [images]
GO
/****** Object:  Table [dbo].[UserOnline]    Script Date: 24-Mar-19 5:16:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserOnline](
	[UserName] [varchar](100) NOT NULL,
	[Activated] [bit] NOT NULL,
 CONSTRAINT [PK__UserOnli__C9F2845775CFEBF8] PRIMARY KEY CLUSTERED 
(
	[UserName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[FassengerMessage]  WITH CHECK ADD FOREIGN KEY([UserName])
REFERENCES [dbo].[FassengerUser] ([UserName])
GO
ALTER TABLE [dbo].[UserOnline]  WITH CHECK ADD  CONSTRAINT [FK__UserOnlin__UserN__3D5E1FD2] FOREIGN KEY([UserName])
REFERENCES [dbo].[FassengerUser] ([UserName])
GO
ALTER TABLE [dbo].[UserOnline] CHECK CONSTRAINT [FK__UserOnlin__UserN__3D5E1FD2]
GO
USE [master]
GO
ALTER DATABASE [Fassenger] SET  READ_WRITE 
GO
