from fastapi import FastAPI
from pydantic import BaseModel
from starlette.requests import Request

from timerRepeat import RepeatTimer
from snake_game import SnakeGame

app = FastAPI()

game = SnakeGame()

MAX_AMOUNT_OF_USERS = len(game.function_matrix)
TIMER_RATE = 0.3


def update_game_state():
    game.update_state()


def user_hash_generate(request):
    client_host = request.client.host
    client_port = request.client.port

    return str(client_host) + "," + str(client_port)


timer = RepeatTimer(TIMER_RATE, update_game_state)

user_list = []

game_on = False


@app.get("/api/hello")
async def hand_shake(request: Request):
    global user_list
    global timer
    global game_on

    response_json = {}

    user_hash = user_hash_generate(request)
    response_json["hash"] = user_hash

    if user_hash in user_list:
        response_json["message"] = f"you are registered already as #" \
                                   f"{user_list.index(user_hash)}"
        response_json["status"] = "ERROR"
        return response_json

    if len(user_list) == MAX_AMOUNT_OF_USERS:
        response_json["message"] = f"DataBase is full with {MAX_AMOUNT_OF_USERS} players!!"
        response_json["status"] = "ERROR"

        return response_json

    user_list.append(user_hash)
    response_json["message"] = f" the user was added successfully to index: " \
                               f"{user_list.index(user_hash)}."
    response_json["status"] = "SUCCESSES"

    if len(user_list) == MAX_AMOUNT_OF_USERS:
        game_on = True
        timer.start()

    return response_json


@app.get("/api/state")
async def get_state_for_painting():
    global game

    if not game_on:
        return {"message": "waiting for other users to join...",
                "type": "WAITING"}

    return {"type": "STATE",
            "massage": game.get_state()}


class SetGameParam(BaseModel):
    function: int
    param: int


@app.put("/api/set_param")
async def set_player_param(input: SetGameParam, request: Request):
    global game

    try:
        name_hash = user_hash_generate(request)

        game.operation_from_function_matrix(user_list.index(name_hash),
                                            input.function, input.param)

        return {"message": f"successfully changed param: {input.param}. by user: {name_hash}",
                "status": "SUCCESSES"}

    except:
        return {"message": f"failed to set param to value {input.param}. by user: {name_hash}",
                "status": "ERROR"}
