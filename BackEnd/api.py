import json

from fastapi import FastAPI
from pydantic import BaseModel
from starlette.requests import Request

from timerRepeat import RepeatTimer
from snake_game import SnakeGame


class DirectionInput(BaseModel):
    new_direction: int


app = FastAPI()

game = SnakeGame()


def update_game_state():
    game.update_state()


def user_hash_generate(request):
    client_host = request.client.host
    # response_json["client_host"] = client_host
    client_port = request.client.port
    # response_json["client_port"] = client_port

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

    if not user_hash in user_list:
        user_list.append(user_hash)
        response_json["user_number"] = str(user_list.index(user_hash))
    else:
        response_json["user_number"] = user_list.index(user_hash)

    return response_json


@app.get("/api/state")
async def get_state_for_painting():
    global game
    return game.get_state()


@app.put("/api/direction")
async def set_direction(input: DirectionInput, request: Request):
    global game

    function_dict = {user_list[0]: game.set_d1,
                     user_list[1]: game.set_d2}

    function_dict[user_hash_generate(request)](input.new_direction)

    return {"massage": f"successfully changed direction{input.new_direction}",
            "port": f"{request.client.port}"}
