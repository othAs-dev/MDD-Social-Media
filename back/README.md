## API documentation
Before starting the project, you will need to run a database for it to work. Several approaches are possible, but the one I recommend is to ensure you have Docker installed on your machine and execute the following command (you can use any database credentials, but make sure to use the exact same ones in the `.env` file you will create. There is a `.env.example` file provided for reference):

`docker run --name mdd-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=mdd -e MYSQL_USER=user -e MYSQL_PASSWORD=pass   -p 3306:3306 -d mysql:latest`

Remember to assign a secret key to the `JWT_SECRET` environment variable. 

##### THE SECRET MUST BE AT LEAST 256 BITS LONG TO ENSURE JWT SECURITY.

If you don't, you will encounter this error: An error occurred while attempting to decode the Jwt: The secret length must be at least **256 bits**

To generate a secret, you have several options. The simplest is to enter this command in your terminal:

`node -e "console.log(require('crypto').randomBytes(32).toString('hex'))"`
or

`openssl rand  -base64 32`
or visit : https://www.grc.com/passwords.htm
