# Web Crawler
A crawler may be a program utilized to gather information from the internet based on given set of domains. When a crawler 
visits an online, site it picks over the complete website’s substance (i.e. the content) and stores it in a information bank. 
It moreover stores all the outside and inside links to the site. The crawler will visit the put away joins at a afterward point 
in time, which is how it moves from one site to the next. By this prepare the crawler captures and lists each site that has 
joins to at slightest one other site.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project.

### Prerequisites

Maven 3.3.0+

Java 8+

Mysql

## Installing
 
 ```shell
Mvn clean install
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
CREATE TABLE job_details 
  ( 
     id           INTEGER PRIMARY KEY auto_increment, 
     created_by   VARCHAR(255), 
     created_date VARCHAR(255), 
     job_name     VARCHAR(255), 
     job_status   VARCHAR(255), 
     url          VARCHAR(255), 
     parent_id    INTEGER, 
     PRIMARY KEY ( id ) 
  ); 
 
## Usage
```
java CrawlManager -domains list.txt -filters filterset.txt -conf jobParameters.config -job jobName
```
## Built With
 
* [Maven](https://maven.apache.org/) - Dependency Management 

## Contributing

 
## Versioning

 
## Authors

* **Sandeep Pediredla** - *Initial work* - [Web_Crawler](https://github.com/sandeep-pediredla/Web_Crawler)

## License

 Copy or use the code as per your requirement

## Acknowledgments
 
