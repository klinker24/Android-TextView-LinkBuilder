package com.klinker.android.link_builder_example;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class HtmlLinkExampleActivity extends AppCompatActivity {

    private static final String TEXT =
            "Here is an example link <a href=\"www.google.com\">www.google.com</a>." +
                    "To show it alongside other LinkBuilder functionality, lets highlight this.";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the content view. Contains a scrollview with a text view inside
        setContentView(R.layout.activity_html_example);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle(R.string.app_name);

        // find the text view. Used to create the link builder
        TextView demo1 = (TextView) findViewById(R.id.demo1);
        TextView demo2 = (TextView) findViewById(R.id.demo2);

        demo1.setText(Html.fromHtml(TEXT));
        demo2.setText(Html.fromHtml(TEXT.replaceAll("</?a[^>]*>", "")));

        // Add the links and make the links clickable
        LinkBuilder.on(demo1)
                .addLinks(getExampleLinks())
                .build();

        LinkBuilder.on(demo2)
                .addLinks(getExampleLinks())
                .build();
    }

    private List<Link> getExampleLinks() {
        List<Link> links = new ArrayList<>();

        Link google = new Link("www.google.com");
        google.setTextColor(Color.parseColor("#00BCD4"));
        google.setHighlightAlpha(.4f);
        google.setOnClickListener(new Link.OnClickListener() {
                    @Override
                    public void onClick(String clickedText) {
                        showToast("clicked: " + clickedText);
                    }
                });

        Link exampleText = new Link("this");
        exampleText.setTextColor(Color.parseColor("#00BCD4"));
        exampleText.setHighlightAlpha(.4f);
        exampleText.setOnClickListener(new Link.OnClickListener() {
            @Override
            public void onClick(String clickedText) {
                showToast("clicked the example text");
            }
        });

        links.add(google);
        links.add(exampleText);

        return links;
    }

    private void openLink(String link) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }

    private void showToast(String text) {
        Toast.makeText(HtmlLinkExampleActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
