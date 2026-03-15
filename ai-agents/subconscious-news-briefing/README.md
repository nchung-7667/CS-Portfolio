# Subconscious AI News Briefing Agent

An autonomous AI agent that generates weekly briefings on the latest developments in artificial intelligence.

The agent performs multi-step research across web sources and synthesizes results into a structured report covering model releases, research breakthroughs, industry developments, and policy updates.

## Features

- Autonomous multi-source AI research
- Structured briefing output
- Multi-hop reasoning across web sources
- Runs with a single agent call

## Tech Stack

- Python
- Subconscious SDK
- Subconscious `web_search` tool
- tim-gpt inference engine

## Installation

Clone the repository:

git clone https://github.com/nchung-7667/CS-Portfolio.git

Navigate to the project:

cd CS-Portfolio/ai-agents/subconscious-news-briefing

Install dependencies:

pip install subconscious-sdk

Set your API key:

export SUBCONSCIOUS_API_KEY=your-api-key

## Usage

Run the agent:

python briefing.py

The agent will automatically research current AI developments and generate a structured briefing in the terminal.

## Project Structure

briefing.py  – agent configuration and execution  
README.md    – project documentation
