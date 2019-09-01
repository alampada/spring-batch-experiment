# Spring batch experiment

Playing around with Spring batch.

Simple spring batch application that:

* reads a list of existing ids from redis
* calls a remote service to get the fresh value for each id / key
* updates redis with the new value