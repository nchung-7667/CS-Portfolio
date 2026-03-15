from subconscious import Subconscious
import os

api_key = os.getenv("SUBCONSCIOUS_API_KEY")

client = Subconscious(api_key)

run = client.run(
    engine="tim-gpt",
    input={
        "instructions": "Search for the latest AI news and summarize the top 3 stories",
        "tools": [{"type": "platform", "id": "web_search"}],
    },
    options={"await_completion": True},
)

print(run.result.answer)