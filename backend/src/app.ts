import express from "express";
import lobbyRouter from "./routes/lobby";
import userRouter from "./routes/user";
import cookieParser from "cookie-parser";
import logger from "morgan";

const app = express();

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());


app.use('/bingo/lobby', lobbyRouter);
app.use('/bingo/user', userRouter);

module.exports = app;
