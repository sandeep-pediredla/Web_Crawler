# Web Crawler
A crawler may be a program utilized to gather information from the internet based on given set of domains. When a crawler 
visits an online, site it picks over the complete website’s substance (i.e. the content) and stores it in a information bank. 
It moreover stores all the outside and inside links to the site. The crawler will visit the put away joins at a afterward point 
in time, which is how it moves from one site to the next. By this prepare the crawler captures and lists each site that has 
joins to at slightest one other site.

## Architecture

![alt text](/docs/img/high_level_overview.JPG "High Level Overview")

## Flow

![alt text](/docs/img/flow.JPG "Data Flow")

## Overview

A Web crawler starts with a list of URLs to visit, called the domain list. As the crawler visits these URLs, it saves the html page to storage repository & it also identifies all the hyperlinks in the page and adds them to the list of URLs to visit, called the child url crawl list. It then indexes the download document which makes search easy. URLs from the child url crawl list are recursively visited according to a set of policies.

The repository only stores HTML pages and these pages are stored as distinct files. A repository is similar to any other system that stores data, like a modern day database. 

## Features

1. Priority of jobs
2. Stop / resume crawl
3. Parallelization 
4. Proxy Support
5. Distributed Crawling
6. Depth rule
7. UI 
8. Binary data support

### Built With
To understand the project, please refer Akka, Jsoup, cli & Tikka frameworks.

## Getting Started

CrawlManager is the main class.


JobManager helps creating Akka actor system & delegates respective messages to different actors.

#### Lists of actors:
- DownloaderActor 
- ParserActor
- DocumentIndexActor
- JobActor
- SaveDocumentActor
- URLExtractorActor
- URLFilterActor

### Prerequisites:

Maven 3.3.0+

Java 8+

Mysql

## Building the project
 
 ```shell
mvn clean install
```
## Configuration
| Configuration Name  | Details | Datatype |
| ------------- | ------------- | ------------- |
|  dbLocation |db location |String |
|  tmpStorageFolder|The folder which will be used by crawler for storing the intermediate crawl data. The content of this folder should not be modified manually. | String|
|  documentStorageFolder| The document storage folder.|String |
|  user|user name | String|

## Deployment
### Database
Creation of job details and links tables are one time task as show below:
 ```mysql

create database crawler_db;

use crawler_db;

CREATE TABLE job_details 
  ( 
     id           INTEGER PRIMARY KEY auto_increment, 
     created_by   VARCHAR(255), 
     created_date VARCHAR(255), 
     job_name     VARCHAR(255), 
     job_status   VARCHAR(255), 
     url          VARCHAR(255), 
     parent_id    INTEGER
  );
  
CREATE TABLE `url_links` (
  `id` int(11) NOT NULL,
  `link` varchar(200) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

```

## Usage
```
java CrawlManager -domains list.txt -filters filterset.txt -conf jobParameters.config -job jobName
```
## Built With
 
* [Maven](https://maven.apache.org/) - Dependency Management 

#### Dependencies:
| Artifact  | Version |
| ------------- | ------------- |
|org.apache.httpcomponents.httpcore|4.4.10|
|org.apache.httpcomponents.httpclient|4.5.6|
|org.slf4j.slf4j-log4j12|1.7.25</version> 
|org.slf4j.slf4j-api|1.7.25|
|commons-io.commons-io|2.6|
|org.apache.tika.tika-parsers|1.18|
|org.apache.tika.tika-core|1.18|
|org.jsoup.jsoup|1.11.3|
|com.typesafe.akka.akka-actor_2.12|2.5.14|
|mysql.mysql-connector-java|5.1.46|
|org.apache.lucene.lucene-core|2.3.0| 
|commons-cli.commons-cli|1.4|

## Contributing

## Versioning

## Furture Plan

1. Cleanup & document code
2. PLan for distributed options other than Akka
3. Junit
4. All above features
 
## Authors

* **Sandeep Pediredla** - *Initial work* - [Web_Crawler](https://github.com/sandeep-pediredla/Web_Crawler)

## License

 Copy or use the code as per your requirement

## Acknowledgments
 
