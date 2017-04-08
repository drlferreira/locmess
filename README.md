#locmess 

##Endpoints 

###/locmess/user

```sh
#Endpoint: /signup 
#Type: Rest
#Excpected Body: {"username" : "ausername", "password" : "apassword"}
#Returns: {"message" : "amessage"} - HttpStatus.OK or HttpStatus.CONFLICT 
#Requires:
```

```sh
#Endpoint: /login 
#Type: Rest
#Excpected Body: {"username" : "ausername", "password" : "apassword"}
#Returns: {"token" : "atoken"} - HttpStatus.OK or HttpStatus.BAD_REQUEST 
#Requires:
```

```sh
#Endpoint: /logout
#Type: Rest
#Excpected Body: {}
#Returns: {}
#Requires: Http header X-Token
```

```sh
#Endpoint: /listpairs
#Type: Rest
#Excpected Body: {}
#Returns: [
            {
              "key": "akey",
              "value": "avalue"
            },
            {
              "key": "akey",
              "value": "avalue"
            }
            ....
          ]
#Requires: Http header X-Token
``` 

```sh
#Endpoint: /addpair | /removepair
#Type: Rest
#Excpected Body: {"key": "akey", "value": "avalue"}
#Returns: {}
#Requires: Http header X-Token
``` 

###/locmess/location

```sh
#Endpoint: /list
#Type: Rest
#Excpected Body: {}
#Returns: [
            {
              "type": "APLocation",
              "name": "eduroam"
            },
            {
              "type": "GPSLocation",
              "name": "Barcelos",
              "latitude": 2,
              "longitude": 3,
              "radius": 5
            }
            ...
          ]
          Note: its an example!
#Requires: Http header X-Token
``` 

```sh
#Endpoint: /create | /remove
#Type: Rest
#Excpected Body: {
                   "type": "APLocation | GPSLocation",
                   (CHECK THE ABOVE EXAMPLE FOR FIELDS NAMES)
                 }
#Returns: {}
#Requires: Http header X-Token
``` 

###/locmess/actions
```sh
#Endpoint: /heartbeat
#Type: Rest
#Excpected Body: {}
#Returns: { TODO: BUT IT WILL BE VALID MESSAGES}
#Requires: Http header X-Token
``` 

PS: For every endpoint where X-Token header is required - if invalid it returns HttpStatus.INTERNAL_SERVER_ERROR