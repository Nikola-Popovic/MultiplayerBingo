FROM node:12-alpine

WORKDIR /app

COPY package.json package-lock.json ./

RUN npm install

COPY tsconfig.json tslint.json ./

COPY src /app/src

COPY bin /app/bin

CMD [ "npm", "start" ]
