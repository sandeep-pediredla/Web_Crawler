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
6. depth rule
7. ui 
8. Binary data support

### Built With
To understand the project, please refer Akka, Jsoup, cli & Tikka frameworks.

## Getting Started
CrawlManager is the main class.
JobManager helps creates Akka actor system & delegates respective messages to different actors.

#### Lists of actors:
- DownloaderActor 
- ParserActor
- DocumentIndexActor
- JobActor
- SaveDocumentActor
- URLExtractorActor
- URLFilterActor

### Prerequisites

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
|  resumableCrawling = false| If this feature is enabled, you would be able to resume a previously stopped/crashed crawl.| boolean|
| long dbLockTimeout | | |
|  maxDepthOfCrawling |Maximum depth of crawling For unlimited depth | int|
|  maxPagesToFetch | Maximum number of pages to fetch For unlimited number of pages, this  parameter should be set to -1.| int|
|  userAgentString | user-agent|String |
|  politenessDelay |politeness delay in milliseconds. a politeness policy that states how to avoid overloading Web sites. | int|
|  includeHttpsPages | Should we also crawl https pages?.| boolean|
|  includeBinaryContentInCrawling |Should we fetch binary content such as images, audio, ...? |boolean |
|  processBinaryContentInCrawling |Should we process binary content such as image, audio, ... using TIKA? |boolean |
|  maxConnectionsPerHost |Maximum Connections per host | int|
|  maxTotalConnections | Maximum total connections.|int |
|  socketTimeout | Socket timeout in milliseconds.| int|
|   connectionTimeout | Connection timeout in milliseconds.|int |
| int maxOutgoingLinksToFollow | Max number of outgoing links which are processed from a page.| int|
|  maxDownloadSize | Max allowed size of a page. Pages larger than this size will not be fetched.|int |
| boolean followRedirects | Should we follow redirects?.| boolean|
|  shutdownOnEmptyQueue |Should the crawler stop running when the queue is empty?. |boolean |
|  threadMonitoringDelaySeconds |Wait this long before checking the status of the worker threads. | int|
|  threadShutdownDelaySeconds | Wait this long to verify the craweler threads are finished working.| int|
|  cleanupDelaySeconds |Wait this long in seconds before launching cleanup. |int |
|  method |The request method type. |String | 
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

## Contributing

 
## Versioning

## Furture Plan
 
## Authors

* **Sandeep Pediredla** - *Initial work* - [Web_Crawler](https://github.com/sandeep-pediredla/Web_Crawler)

## License

 Copy or use the code as per your requirement

## Acknowledgments
 
