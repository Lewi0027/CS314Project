import memberPic from "@static/images/Placeholder.jpg";
import teamPic from "@static/images/PlaceholderTeam.jpg";

import bscheidtPic from "@static/images/bscheidt_team_image.jpg"
import hlewisPic from "@static/images/hlewis_team_image.png"
import wattg5Pic from "@static/images/wyattg5_team_image.jpg"

export const teamData =
    {
        teamName: "Team Name",
        missionStatement: "Mission Statement",
        imagePath: teamPic,
    };


export const memberData = [
    {
        name: "Brendan Scheidt",
        bio: "I am an undergrad at CSU majoring in computer science. I am also a UTA for CS165 data structures and algorithms. Throughout my career, I want to help shape the future of artificial intelligence by leading projects that push the boundaries of what's possible in fields such as education and environmental studies. To start accomplishing these goals, last year, I wrote a website that is an AI chatbot for helping tutor students in their classes and have it deployed on Heroku. Outside of school, I enjoy producing music and camping.",
        homeTown: "Fort Collins, CO",
        imagePath: bscheidtPic
    },
    {
        name: "Wyatt Gensler",
        bio: "I am an undergrad student at CSU majoring in computer science and business finance. I am currently applying to internships in both of these industries. After graduation I hope to get a job with an aerospace company and after a few years in the industry I want to put my business and finance knowledge to work by moving up into a more managerial or corporate role. Outside of school I enjoy everything outdoors related, especially racing motocross.",
        homeTown: "Castle Rock, CO",
        imagePath: wattg5Pic
    },
    {
        name: "Team Member 3",
        bio: "Bio Here",
        homeTown: "Hometown",
        imagePath: hlewisPic
    },
    {
        name: "Team Member 4",
        bio: "Bio Here",
        homeTown: "Hometown",
        imagePath: memberPic
    },
    {
        name: "Team Member 5",
        bio: "Bio Here",
        homeTown: "Hometown",
        imagePath: memberPic
    },
];