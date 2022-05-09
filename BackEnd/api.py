from fastapi import FastAPI
from pydantic import BaseModel
from starlette.requests import Request

from timerRepeat import RepeatTimer
from snake_game import SnakeGame

app = FastAPI()

game = SnakeGame()

MAX_AMOUNT_OF_USERS = len(game.function_matrix)


def update_game_state():
    game.update_state()


def user_hash_generate(request):
    client_host = request.client.host
    client_port = request.client.port

    return str(client_host) + "," + str(client_port)


timer = RepeatTimer(1, update_game_state)
timer.start()

user_list = []


@app.get("/api/hello")
async def hand_shake(request: Request):
    global user_list

    response_json = {}

    user_hash = user_hash_generate(request)
    response_json["hash"] = user_hash

    if user_hash in user_list:
        response_json["error_adding"] = f"you are registered already as #" \
                                        f"{user_list.index(user_hash)}"
        return response_json

    if len(user_list) == MAX_AMOUNT_OF_USERS:
        response_json["error_adding"] = "no room for more players!!"
        return response_json

    user_list.append(user_hash)
    response_json["user_number"] = str(user_list.index(user_hash))

    return response_json


@app.get("/api/state")
async def get_state_for_painting():
    global game
    return game.get_state()


class SetGameParam(BaseModel):
    function: int
    param: int


@app.put("/api/set_param")
async def set_player_param(input: SetGameParam, request: Request):
    global game

    game.operation_from_function_matrix(user_list.index(user_hash_generate(request)),
                                        input.function, input.param)

    return {"massage": f"successfully changed param {input.param}",
            "port": f"{request.client.port}"}
