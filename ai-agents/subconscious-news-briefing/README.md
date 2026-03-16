# Subconscious AI News Briefing Agent

An autonomous multi-agent research system that generates weekly briefings on the latest developments in artificial intelligence.

Three specialized agents work in sequence — one scouts the web across multiple angles, one analyzes and connects the findings, and one writes a polished report.

## Features

- Multi-agent pipeline with strict separation of concerns
- Multi-hop web research across model releases, research, industry, and policy
- Long-context reasoning across accumulated findings
- Saves final report as a markdown file

## Tech Stack

- Python
- Subconscious SDK
- Subconscious `web_search` tool
- tim-gpt inference engine

## Installation

Clone the repository:
```bash
git clone https://github.com/nchung-7667/CS-Portfolio.git
```

Navigate to the project:
```bash
cd CS-Portfolio/ai-agents/subconscious-news-briefing
```

Install dependencies:
```bash
pip install subconscious-sdk
```

Set your API key:
```bash
export SUBCONSCIOUS_API_KEY=your-api-key
```

## Usage
```bash
python ai-news-agent.py
```

The agent will research current AI developments and save a structured briefing to `briefing_report.md`.

## Project Structure
```
ai-news-agent.py       — three-agent pipeline (scout, analyst, writer)
briefing_report.md     — sample output report
README.md              — this file
```

## Sample Output

See [briefing_report.md](briefing_report.md) for a full example of the agent's output.
