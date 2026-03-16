from subconscious import Subconscious
import os

client = Subconscious(os.getenv("SUBCONSCIOUS_API_KEY"))

TOPIC = "AI agent infrastructure and tooling"

# Agent 1: Scout 
print("Agent 1: Scouting sources...")

scout = client.run(
    engine="tim-gpt",
    input={
        "instructions": f"""You are a research scout. Your job is to gather raw information. DO not analyze or summarize.

Search broadly across these angles for the topic: {TOPIC}
- Recent model releases or updates
- New research papers or findings
- Funding rounds, acquisitions, or major product launches
- Policy or regulatory developments
- Notable commentary or debate in the field

Run multiple searches across all angles. Return everything you find as structured raw notes:
- Source name
- Headline or title
- Key facts or data points
- URL if available

Do not analyze or editorialize. Just gather.""",
        "tools": [{"type": "platform", "id": "web_search"}],
    },
    options={"await_completion": True},
)

raw_findings = scout.result.answer
print("Scout complete.\n")


#  Agent 2: Analyst 
print(" Agent 2: Analyzing findings...")

analyst = client.run(
    engine="tim-gpt",
    input={
        "instructions": f"""You are a research analyst. You do not search the web. You only analyze what you are given.

Here are the raw findings from a research scout on the topic of {TOPIC}:

{raw_findings}

Your job:
1. Identify the 5 most significant developments and explain why each matters
2. Find connections or patterns across the findings; what themes are emerging?
3. Flag anything that seems underreported but potentially important
4. Note any contradictions or tensions between sources
5. Produce a structured analytical brief; bullet points and short paragraphs, no fluff

Do not add new information. Only reason over what you have been given.""",
        "tools": [],
    },
    options={"await_completion": True},
)

analysis = analyst.result.answer
print(" Analysis complete.\n")


#  Agent 3: Writer 
print(" Agent 3: Writing report...")

writer = client.run(
    engine="tim-gpt",
    input={
        "instructions": f"""You are a research writer. You do not search or analyze just you write.

Using the analytical brief below, write a polished weekly briefing report on: {TOPIC}

Analytical brief:
{analysis}

Structure the report as follows:
- A 2-3 sentence executive summary at the top
- Key Developments: the most significant things that happened this week
- Emerging Themes: patterns and trends across the findings
- Under the Radar: anything underreported worth watching
- The Big Picture: a 2-3 sentence closing synthesis on what this all means for the field

Write in clear, professional prose. No bullet soup. Make it something someone would actually want to read.""",
        "tools": [],
    },
    options={"await_completion": True},
)

report = writer.result.answer
print("Report complete.\n")


#  Output 
print("=" * 60)
print(f"WEEKLY BRIEFING: {TOPIC.upper()}")
print("=" * 60)
print(report)

# Optionally save to file
with open("briefing_report.md", "w") as f:
    f.write(f"# Weekly Briefing: {TOPIC}\n\n")
    f.write(report)

print("\n Report saved to briefing_report.md")