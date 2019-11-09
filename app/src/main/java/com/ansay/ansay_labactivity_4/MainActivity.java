package com.ansay.ansay_labactivity_4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    String[] compNames, compCountries, compCEOs, compIndustries,compDescriptions;
    int[] logos = {R.drawable.bmw, R.drawable.ford, R.drawable.honda, R.drawable.hyundai, R.drawable.kia,
            R.drawable.mitsubishi, R.drawable.nissan, R.drawable.peugeot, R.drawable.porsche, R.drawable.renault,
            R.drawable.subaru, R.drawable.suzuki, R.drawable.toyota, R.drawable.volkswagen};

    ArrayList<TopCompanies> companies = new ArrayList<>();

    ListView listCompanies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("TOP GLOBAL COMPANIES");

        compNames = getResources().getStringArray(R.array.companies);
        compCountries = getResources().getStringArray(R.array.country);
        compCEOs = getResources().getStringArray(R.array.ceo);
        compIndustries = getResources().getStringArray(R.array.industry);
        compDescriptions = getResources().getStringArray(R.array.info);

        for (int i=0; i < compNames.length; i++){
            companies.add(i, new TopCompanies(logos[i], compNames[i], compCountries[i],compCEOs[i],compIndustries[i],compDescriptions[i]));
        }

        CompanyAdapter adapter = new CompanyAdapter(this, R.layout.company,companies);

        listCompanies = findViewById(R.id.lvCompanies);
        listCompanies.setAdapter(adapter);
        listCompanies.setOnItemClickListener(this);

    }

    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long id) {
        final File folder = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(folder, "companies.txt");

        try {
            FileOutputStream fos = new FileOutputStream(file);
            String name = companies.get(i).getCompName();
            String country = companies.get(i).getCompCountry();
            String ceo = companies.get(i).getCompCEO();
            String industry = companies.get(i).getCompIndustry();
            String description = companies.get(i).getCompDesc();

            fos.write((name + "\n").getBytes());
            fos.write((country + "\n").getBytes());
            fos.write((ceo + "\n").getBytes());
            fos.write((industry + "\n").getBytes());
            fos.write((description + "\n").getBytes());



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setIcon(logos[i]);
        dialog.setTitle(compNames[i]);
        dialog.setMessage(compDescriptions[i]);
        dialog.setNeutralButton("CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dialog.dismiss();
                File file = new File(folder, "companies.txt");
                StringBuilder sb = new StringBuilder();

               String compNames = null;
                String compCountry = null;
                String compCEOs = null;
                String compIndustry = null;
                String compDescription = null;

                try{
                    BufferedReader br = new BufferedReader(new FileReader(file));

                    for (int i =0;i<1;i++){
                       compNames = br.readLine();
                    }
                    for (int i =0;i<1;i++){
                        compCountry = br.readLine();
                    }
                    for (int i =0;i<1;i++){
                        compCEOs = br.readLine();
                    }
                    for (int i =0;i<1;i++){
                        compIndustry = br.readLine();
                    }
                    for (int i =0;i<1;i++){
                        compDescription = br.readLine();
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(MainActivity.this, compNames + "\n" + compCountry + "\n" + compCEOs + "\n" + compIndustry + "\n" + compDescription, Toast.LENGTH_LONG).show();
            }
        });
        dialog.create().show();
    }
}
