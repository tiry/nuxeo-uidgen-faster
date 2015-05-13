## About

This module contribute `UIDSequencer` implementation based on Elasticsearch and Redis.

## Using it

Build & deploy the module.


To use Redis :

        UIDGeneratorService service = Framework.getService(UIDGeneratorService.class);
        UIDSequencer seq = service.getSequencer("redisSequencer");
        seq.getNext(key);

To use Elasticsearch :

        UIDGeneratorService service = Framework.getService(UIDGeneratorService.class);
        UIDSequencer seq = service.getSequencer("esSequencer");
        seq.getNext(key);


